package com.anadolstudio.view.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.view.R
import com.anadolstudio.view.databinding.ViewBottomTopLineBinding

class BottomTopLine @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewBottomTopLineBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.BottomTopLine, defStyleAttr, 0) {
            binding.imageView.imageTintList = getColorStateList(R.styleable.BottomTopLine_android_tint)
        }
    }

}
