package com.anadolstudio.view.toolbar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import androidx.core.view.isVisible
import com.anadolstudio.utils.util.common.throttleClick
import com.anadolstudio.utils.util.extentions.setTextOrMakeGoneIfBlank
import com.anadolstudio.view.R
import com.anadolstudio.view.databinding.ViewToolbarBinding

class BaseToolbar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val NO_RESOURCE = 0
    }

    private val binding: ViewToolbarBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_toolbar, this)
        binding = ViewToolbarBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.BaseToolbar, defStyleAttr, 0) {
            setTitle(getString(R.styleable.BaseToolbar_title))
            setDescription(getString(R.styleable.BaseToolbar_description))

            getDrawable(R.styleable.BaseToolbar_back_icon)?.let {
                setBackIcon(it)
                setBackIconVisible(true)
            }

            isDividerVisible(getBoolean(R.styleable.BaseToolbar_needDivider, true))

            setTextAppearance(getResourceId(R.styleable.BaseToolbar_textAppearance, NO_RESOURCE))
            setTintColor(getColor(R.styleable.BaseToolbar_tint, Color.BLACK))

            val tint = getColor(R.styleable.BaseToolbar_iconTint, Color.TRANSPARENT)
            if (tint != Color.TRANSPARENT) binding.toolbarBackButton.setTint(tint)
        }
    }

    fun isDividerVisible(isVisible: Boolean) {
        binding.divider.isVisible = isVisible
    }

    private fun setTintColor(color: Int) {
        with(binding) {
            toolbarTitle.setTextColor(color)
            toolbarDescription.setTextColor(color)
        }
    }

    override fun onFinishInflate() {
        val translatedViews = mutableListOf<View>()
        children.forEach { view ->
            if (view.parent != binding.mainContainer && view != binding.mainContainer) {
                translatedViews.add(view)
            }
        }

        translatedViews.forEach { view ->
            removeViewInLayout(view)
            binding.toolbarIconContainer.addView(view)
        }

        super.onFinishInflate()
    }

    fun addIconButton(drawable: Drawable?, listener: (View) -> Unit) {
        val view = ToolbarIconButton(context).also { button ->
            button.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            button.throttleClick { listener.invoke(button) }
            button.setDrawable(drawable)
        }

        binding.toolbarIconContainer.addView(view)
    }

    fun setBackIconVisible(isVisible: Boolean) {
        binding.toolbarBackButton.isVisible = isVisible
    }

    fun setBackClickListener(listener: () -> Unit) = binding.toolbarBackButton.throttleClick { listener.invoke() }

    fun setTitle(text: String?) = binding.toolbarTitle.setTextOrMakeGoneIfBlank(text)

    fun setDescription(text: String?) = binding.toolbarDescription.setTextOrMakeGoneIfBlank(text)

    fun setBackIcon(drawable: Drawable?) = binding.toolbarBackButton.setDrawable(drawable)

    fun setTextAppearance(@StyleRes id: Int) = binding.toolbarTitle.setTextAppearance(id)

}
