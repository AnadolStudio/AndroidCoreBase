package com.anadolstudio.core.view.bottom

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.core.R
import com.anadolstudio.core.databinding.ViewBottomTopLineBinding

class BottomTopLine @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewBottomTopLineBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.BottomTopLine, defStyleAttr, 0) {
            backgroundTintList = getColorStateList(R.styleable.BottomTopLine_android_tint)
        }
    }

    override fun setBackgroundTintList(tint: ColorStateList?) {
        binding.imageView.imageTintList = tint
    }
}
