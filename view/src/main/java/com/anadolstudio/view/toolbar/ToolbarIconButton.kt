package com.anadolstudio.view.toolbar

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.view.R
import com.anadolstudio.view.databinding.ViewToolbarIconButtonBinding

class ToolbarIconButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewToolbarIconButtonBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_toolbar_icon_button, this)
        binding = ViewToolbarIconButtonBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.ToolbarIconButton, defStyleAttr, 0) {
            binding.button.setImageDrawable(getDrawable(R.styleable.ToolbarIconButton_src))
        }
    }

    fun setDrawable(drawable: Drawable?) = binding.button.setImageDrawable(drawable)

    fun setTint(color: Int?) {
        binding.button.imageTintList = color?.let { ColorStateList.valueOf(it) }
    }

    override fun setOnClickListener(l: OnClickListener?) = binding.button.setOnClickListener(l)
}
