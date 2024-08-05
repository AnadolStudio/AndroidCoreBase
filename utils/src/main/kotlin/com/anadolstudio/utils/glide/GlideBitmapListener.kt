package com.anadolstudio.utils.glide

import android.graphics.Bitmap
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideBitmapListener(
        private val onSuccessAction: ((bitmap: Bitmap) -> Unit),
        private val onErrorAction: (() -> Unit)
) : RequestListener<Bitmap?> {

    override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap?>,
            isFirstResource: Boolean
    ): Boolean {
        onErrorAction.invoke()

        return false
    }

    override fun onResourceReady(
            resource: Bitmap?,
            model: Any,
            target: Target<Bitmap?>,
            dataSource: DataSource,
            isFirstResource: Boolean
    ): Boolean {
        resource?.let { onSuccessAction.invoke(it) }

        return false
    }
}
