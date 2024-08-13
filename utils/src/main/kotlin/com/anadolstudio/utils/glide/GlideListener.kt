package com.redmadrobot.app.utils.glide

import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideListener(
        private val onSuccess: () -> Unit = {},
        private val onError: () -> Unit = {},
) : RequestListener<Drawable> {
    override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>,
            isFirstResource: Boolean
    ): Boolean = false.also {
        onError.invoke()
    }

    /**
     * Вызывается, если успешно загрузилась картинка по ссылке
     * ВАЖНО!!! Если ImageView изначально имело visibility == GONE || INVISIBLE, то этот метод не вызовется.
     * Поэтому загружать нужно через target, но даже так, может проявлятся некорректное поведение
     *
     * https://stackoverflow.com/questions/32503327/glide-listener-doesnt-work
     */
    override fun onResourceReady(
            resource: Drawable,
            model: Any,
            target: Target<Drawable>?,
            dataSource: DataSource,
            isFirstResource: Boolean
    ): Boolean = false.also {
        onSuccess.invoke()
    }

}
