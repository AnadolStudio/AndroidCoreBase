package com.anadolstudio.view.stub

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.anadolstudio.utils.util.common.dpToPx
import com.anadolstudio.view.R
import com.anadolstudio.view.databinding.ViewErrorBinding

class ErrorView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private companion object {
        val DEFAULT_SIDE = 100F.dpToPx()
    }

    private val binding: ViewErrorBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_error, this)
        binding = ViewErrorBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, 0) {
            setTitle(getString(R.styleable.ErrorView_title))
            setImage(getDrawable(R.styleable.ErrorView_src))
            setImageSize(getDimension(R.styleable.ErrorView_srcSide, DEFAULT_SIDE))
            setTitleAppearance(getResourceId(R.styleable.ErrorView_titleTextAppearance, 0))

            binding.button.apply {
                isVisible = getBoolean(R.styleable.ErrorView_buttonVisible, true)
                setTextColor(getColor(R.styleable.ErrorView_buttonTint, context.getColor(R.color.colorAccent)))
                setText(getText(R.styleable.ErrorView_buttonText))
                setEnableColor(getColor(R.styleable.ErrorView_buttonBackgroundColor, context.getColor(R.color.colorPrimary)))
                setDisableBackgroundColor(getColor(R.styleable.ErrorView_buttonDisableBackgroundColor, context.getColor(R.color.disableForeground)))
                setDisableForegroundColor(getColor(R.styleable.ErrorView_buttonDisableForegroundColor, context.getColor(R.color.disableBackground)))
            }
        }
    }

    fun setImage(drawable: Drawable?) {
        binding.image.setImageDrawable(drawable)
    }

    fun setImageSize(sidePx: Float) {
        binding.image.layoutParams.height = sidePx.toInt()
        binding.image.layoutParams.width = sidePx.toInt()
        binding.image.requestLayout()
    }

    fun setTitle(string: String?) {
        binding.title.text = string
    }

    fun setTitleAppearance(id: Int) {
        binding.title.setTextAppearance(id)
    }

    override fun setOnClickListener(l: OnClickListener?) = binding.button.setOnClickListener(l)
}
