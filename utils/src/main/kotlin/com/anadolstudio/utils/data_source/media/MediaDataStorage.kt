package com.anadolstudio.utils.data_source.media

import android.content.ContentResolver.QUERY_ARG_LIMIT
import android.content.ContentResolver.QUERY_ARG_OFFSET
import android.content.ContentResolver.QUERY_ARG_SORT_COLUMNS
import android.content.ContentResolver.QUERY_ARG_SORT_DIRECTION
import android.content.ContentResolver.QUERY_ARG_SQL_GROUP_BY
import android.content.ContentResolver.QUERY_ARG_SQL_SELECTION
import android.content.ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS
import android.content.ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore.Images.ImageColumns
import android.provider.MediaStore.Images.Media
import android.provider.MediaStore.MediaColumns
import androidx.core.database.getStringOrNull
import com.anadolstudio.utils.util.extentions.nullIfEmpty
import org.joda.time.DateTime
import java.io.File

class MediaDataStorage(private val context: Context) {

    private companion object {
        const val AND = " and "
        val legalFormat = listOf("image/jpg", "image/jpeg", "image/png", "image/webp")
    }

    fun loadImages(
        pageIndex: Int,
        pageSize: Int,
        folder: String?,
    ): List<Image> {

        val images = mutableListOf<Image>()

        val uri: Uri = Media.EXTERNAL_CONTENT_URI
        val offset = pageSize * pageIndex

        val projection = arrayOf(
            MediaColumns._ID,
            MediaColumns.DATA,
            MediaColumns.MIME_TYPE,
            Media.BUCKET_DISPLAY_NAME,
        )

        val selectionArgs = mutableListOf<String>()
        val selectionBuilder = StringBuilder()

        initImageSelection(folder, selectionArgs, selectionBuilder)

        val selection = if (selectionBuilder.isNotBlank()) selectionBuilder.toString() else null
        val cursor =
            getPageGalleryCursor(uri, projection, selection, selectionArgs, pageSize, offset)

        cursor?.use {
            val idIndex = cursor.getColumnIndexOrThrow(MediaColumns._ID)
            val dataIndex = cursor.getColumnIndexOrThrow(MediaColumns.DATA)
            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaColumns.MIME_TYPE)
            val bucketDisplayNameIndex =
                cursor.getColumnIndexOrThrow(MediaColumns.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val path = cursor.getStringOrNull(dataIndex)
                val date = path?.let { File(it) }?.lastModified()

                val image = Image(
                    path = Uri.withAppendedPath(uri, cursor.getString(idIndex)).toString(),
                    date = date?.let(::DateTime) ?: DateTime(0L),
                    folder = cursor.getStringOrNull(bucketDisplayNameIndex),
                    format = cursor.getStringOrNull(mimeTypeIndex),
                )

                images.add(image)
            }
        }

        return images
    }

    private fun getPageGalleryCursor(
        uri: Uri,
        projection: Array<String>,
        selection: String?,
        selectionArgs: List<String>,
        pageSize: Int,
        offset: Int
    ): Cursor? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver.query(
            uri.buildUpon().encodedQuery("limit=$offset,$pageSize").build(),
            projection,
            Bundle().apply {
                // Limit & Offset
                putInt(QUERY_ARG_LIMIT, pageSize)
                putInt(QUERY_ARG_OFFSET, offset)
                // sort
                putStringArray(QUERY_ARG_SORT_COLUMNS, arrayOf(MediaColumns.DATE_MODIFIED))
                putInt(QUERY_ARG_SORT_DIRECTION, QUERY_SORT_DIRECTION_DESCENDING)
                // Selection
                putString(QUERY_ARG_SQL_SELECTION, selection)
                putStringArray(
                    QUERY_ARG_SQL_SELECTION_ARGS,
                    selectionArgs.toTypedArray().nullIfEmpty()
                )
            }, null
        )
    } else {
        context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs.toTypedArray().nullIfEmpty(),
            "${MediaColumns.DATE_MODIFIED} DESC LIMIT $pageSize OFFSET $offset"
        )
    }

    private fun initImageSelection(
        folder: String?,
        selectionArg: MutableList<String>,
        selectionBuilder: StringBuilder,
    ) {
        val mimeTypeSelection = legalFormat.joinToString(
            prefix = "${MediaColumns.MIME_TYPE} = ? ",
            separator = " OR ${MediaColumns.MIME_TYPE} = ? ",
            transform = { "" }
        )

        if (folder != null) {
            selectionArg.add(folder)
            selectionBuilder.append("${Media.BUCKET_DISPLAY_NAME} = ?")
        }

        selectionArg.addAll(legalFormat)

        if (folder != null) {
            selectionBuilder.appendNext("($mimeTypeSelection)")
        } else {
            selectionBuilder.appendNext(mimeTypeSelection)
        }
    }

    fun loadFolders(): Set<Folder> {
        val folders = mutableSetOf<Folder>()

        val uri: Uri = Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            ImageColumns.BUCKET_ID,
            MediaColumns.DATE_MODIFIED,
            Media.BUCKET_DISPLAY_NAME,
            Media.DATA,
        )

        val cursor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.query(
                uri,
                projection,
                Bundle().apply {
                    putStringArray(QUERY_ARG_SORT_COLUMNS, arrayOf(MediaColumns.DATE_MODIFIED))
                    putInt(QUERY_ARG_SORT_DIRECTION, QUERY_SORT_DIRECTION_DESCENDING)
                    putString(QUERY_ARG_SQL_GROUP_BY, MediaColumns.DATE_MODIFIED)
                }, null
            )
        } else {
            context.contentResolver.query(
                uri,
                projection,
                "1) GROUP BY (${MediaColumns.DATE_MODIFIED}",
                null,
                "${MediaColumns.DATE_MODIFIED} DESC"
            )
        }

        cursor?.use {
            val folderNameIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME)
            val thumbIndex = cursor.getColumnIndexOrThrow(Media.DATA)
            val setFolders = mutableSetOf<String>()

            while (cursor.moveToNext()) {
                val path = Uri.withAppendedPath(uri, cursor.getString(folderNameIndex)).path
                val name = File(requireNotNull(path)).name

                if (setFolders.contains(name)) continue
                setFolders.add(name)

                folders.add(
                    Folder(
                        name = name,
                        thumbPath = cursor.getString(thumbIndex),
                        imageCount = getCountImagesByFolder(name)
                    )
                )
            }
        }

        return folders
    }

    fun getCountImagesByFolder(folder: String?): Int {
        val uri: Uri = Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaColumns._ID,
            MediaColumns.MIME_TYPE,
            Media.BUCKET_DISPLAY_NAME,
        )

        val selectionArgs = mutableListOf<String>()
        val selectionBuilder = StringBuilder()

        initImageSelection(folder, selectionArgs, selectionBuilder)

        val selection = if (selectionBuilder.isNotBlank()) selectionBuilder.toString() else null
        val cursor = getCountImagesByFolder(uri, projection, selection, selectionArgs)

        return cursor?.count ?: 0
    }

    private fun getCountImagesByFolder(
        uri: Uri,
        projection: Array<String>,
        selection: String?,
        selectionArgs: List<String>
    ): Cursor? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver.query(
            uri,
            projection,
            Bundle().apply {
                // Selection
                putString(QUERY_ARG_SQL_SELECTION, selection)
                putStringArray(
                    QUERY_ARG_SQL_SELECTION_ARGS,
                    selectionArgs.toTypedArray().nullIfEmpty()
                )
            }, null
        )
    } else {
        context.contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs.toTypedArray().nullIfEmpty(),
            null
        )
    }

    private fun StringBuilder.appendNext(text: String) {
        if (this.isNotEmpty()) append(AND)

        append(text)
    }
}
