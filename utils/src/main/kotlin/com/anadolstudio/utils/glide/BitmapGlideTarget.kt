package com.anadolstudio.utils.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class BitmapGlideTarget(val width: Int, val height: Int) : CustomTarget<Bitmap>(width, height) {

    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) = Unit

    override fun onLoadCleared(placeholder: Drawable?) = Unit
}
