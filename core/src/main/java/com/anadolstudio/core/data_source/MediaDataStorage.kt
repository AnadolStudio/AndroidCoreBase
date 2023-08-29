package com.anadolstudio.core.data_source

import android.content.ContentResolver.*
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.anadolstudio.core.common_extention.nullIfEmpty
import com.anadolstudio.core.rx_util.singleFrom
import io.reactivex.Single
import java.io.File

class MediaDataStorage(private val context: Context) {

    private companion object {
        const val AND = " and "
        const val FORMAT = 1
        val legalFormat = listOf("jpg", "jpeg", "png", "webp")
    }

    fun loadImages(
            pageIndex: Int,
            pageSize: Int,
            folder: String?,
    ): Single<List<String>> = singleFrom {

        val images = mutableListOf<String>()

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val offset = pageSize * pageIndex

        val projection = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        val selectionArgs = mutableListOf<String>()
        val selectionBuilder = StringBuilder()

        initSelection(folder, selectionArgs, selectionBuilder)

        val selection = if (selectionBuilder.isNotBlank()) selectionBuilder.toString() else null
        val cursor = getCursor(uri, projection, selection, selectionArgs, pageSize, offset)

        cursor?.use {
            val columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            val columnDataIndex1 = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE) // TODO filter MIME_TYPE

            while (cursor.moveToNext()) {
                println(cursor.getString(columnDataIndex1))
                images.add(Uri.withAppendedPath(uri, cursor.getString(columnDataIndex)).toString())
            }
        }

        return@singleFrom images
    }

    private fun getCursor(
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
                    putStringArray(QUERY_ARG_SORT_COLUMNS, arrayOf(MediaStore.MediaColumns._ID))
                    putInt(QUERY_ARG_SORT_DIRECTION, QUERY_SORT_DIRECTION_DESCENDING)
                    // Selection
                    putString(QUERY_ARG_SQL_SELECTION, selection)
                    putStringArray(QUERY_ARG_SQL_SELECTION_ARGS, selectionArgs.toTypedArray().nullIfEmpty())
                }, null
        )
    } else {
        context.contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs.toTypedArray().nullIfEmpty(),
                "${MediaStore.MediaColumns._ID} DESC LIMIT $pageSize OFFSET $offset"
        )
    }

    private fun initSelection(
            folder: String?,
            selectionArg: MutableList<String>,
            selectionBuilder: StringBuilder,
    ) {
        if (folder != null) {
            selectionArg.add(folder)
            selectionBuilder.append("${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?")
        }

        /*val mimeTypeSelection = legalFormat.joinToString(
                prefix = "${MediaStore.MediaColumns.MIME_TYPE} == ",
                separator = " OR ${MediaStore.MediaColumns.MIME_TYPE} == ",
                transform = { it }
        )
        selectionBuilder.appendNext(mimeTypeSelection)*/
    }

    fun loadFolders(): Single<Set<String>> = singleFrom {
        val folders = mutableSetOf<String>()

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        context.contentResolver.query(
                uri,
                projection,
                null,
                null,
                "${MediaStore.MediaColumns._ID} DESC"
        )?.use { cursor ->
            val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            while (cursor.moveToNext()) {
                Uri.withAppendedPath(uri, cursor.getString(columnIndexData)).path?.also { path ->
                    folders.add(File(path).name)
                }
            }
        }

        return@singleFrom folders
    }

    private fun StringBuilder.appendNext(text: String) {
        if (this.isNotEmpty()) append(AND)

        append(text)
    }
}
