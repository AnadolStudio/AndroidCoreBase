package com.anadolstudio.core.view.toolbar

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import androidx.core.content.withStyledAttributes
import com.anadolstudio.core.R
import com.anadolstudio.core.common_util.throttleClick
import com.anadolstudio.core.databinding.ViewToolbarBinding

open class BaseToolbar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    companion object {
        private const val NO_RESOURCE = 0
    }

    private val binding: ViewToolbarBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_toolbar, this)
        binding = ViewToolbarBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.BaseToolbar, defStyleAttr, 0) {
            setTitle(getString(R.styleable.BaseToolbar_title))
            getDrawable(R.styleable.BaseToolbar_back_icon)?.let(this@BaseToolbar::setBackIcon)
            setTextAppearance(getResourceId(R.styleable.BaseToolbar_textAppearance, NO_RESOURCE))
        }
    }

    fun addIconButton(drawable: Drawable?, listener: (View) -> Unit) {
        val view = ToolbarIconButton(context).also { button ->
            button.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            button.throttleClick { listener.invoke(button) }
            button.setDrawable(drawable)
        }

        binding.toolbarIconContainer.addView(view)
    }

    fun setBackClickListener(listener: () -> Unit) = binding.toolbarBackButton.throttleClick { listener.invoke() }

    fun setTitle(title: String?) = binding.toolbarTitle.setText(title)

    fun setBackIcon(drawable: Drawable?) = binding.toolbarBackButton.setDrawable(drawable)

    fun setTextAppearance(@StyleRes id: Int) = binding.toolbarTitle.setTextAppearance(id)

}
