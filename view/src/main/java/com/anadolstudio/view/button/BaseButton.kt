package com.anadolstudio.view.button

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.anadolstudio.utils.R.*
import com.anadolstudio.utils.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.utils.util.extentions.setDimensMargins
import com.anadolstudio.view.R
import com.anadolstudio.view.databinding.ViewBaseButtonBinding

open class BaseButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewBaseButtonBinding.inflate(LayoutInflater.from(context), this)
    private var state: State = State.ENABLE
    private var enableColor: Int = context.getColor(R.color.colorPrimary)
    private var enableTextColor: Int = context.getColor(R.color.colorAccent)
    private var disableForegroundColor: Int = context.getColor(R.color.disableForeground)
    private var disableBackgroundColor: Int = context.getColor(R.color.disableBackground)

    init {
        context.withStyledAttributes(attrs, R.styleable.BaseButton, defStyleAttr, defStyleRes) {
            initButtonText(this)
            enableColor = getColor(R.styleable.BaseButton_backgroundColor, context.getColor(R.color.colorPrimary))
            disableForegroundColor = getColor(R.styleable.BaseButton_disableForegroundColor, context.getColor(R.color.disableForeground))
            disableBackgroundColor = getColor(R.styleable.BaseButton_disableBackgroundColor, context.getColor(R.color.disableBackground))

            val paddingTop = getDimension(
                    R.styleable.BaseButton_paddingTop,
                    context.resources.getDimension(dimen.padding_main)
            )
            val paddingBottom = getDimension(
                    R.styleable.BaseButton_paddingBottom,
                    context.resources.getDimension(dimen.padding_main)
            )

            binding.cardView.setDimensMargins(top = paddingTop.toInt(), bottom = paddingBottom.toInt())
            changeState(isEnabled)
        }
    }

    private fun initButtonText(attrs: TypedArray) = with(attrs) {
        setTextColor(getColor(R.styleable.BaseButton_tint, context.getColor(R.color.colorAccent)))
        setText(getText(R.styleable.BaseButton_text))
    }

    override fun setEnabled(enabled: Boolean) {
        changeState(enabled)
        super.setEnabled(enabled)
        binding.title.isEnabled = enabled
        binding.cardView.isEnabled = enabled
    }

    private fun changeState(enabled: Boolean) {
        state = if (enabled) State.ENABLE else State.DISABLE

        when (state) {
            State.ENABLE -> {
                binding.title.setTextColor(enableTextColor)
                binding.cardView.setCardBackgroundColor(enableColor)
                binding.cardView.cardElevation = context.resources.getDimension(dimen.elevation_normal)
            }

            State.DISABLE -> {
                binding.title.setTextColor(disableForegroundColor)
                binding.cardView.setCardBackgroundColor(disableBackgroundColor)
                binding.cardView.cardElevation = 0F
            }
        }
    }

    private fun invalidateState() = changeState(isEnabled)

    override fun setOnClickListener(l: OnClickListener?) {
        binding.cardView.scaleAnimationOnClick { l?.onClick(binding.cardView) }
    }

    fun setText(text: CharSequence?) {
        binding.title.text = text
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setOnTouchListener(l: OnTouchListener?) {
        binding.cardView.setOnTouchListener(l)
    }

    fun setText(@StringRes textRes: Int) = setText(context.getString(textRes))

    fun setLoading(isLoading: Boolean) {
        super.setEnabled(!isLoading)
        binding.progressContainer.isVisible = isLoading
    }

    fun setTextColorRes(@ColorRes colorRes: Int) = setTextColor(context.getColor(colorRes))

    fun setTextColor(@ColorInt color: Int) {
        enableTextColor = color
        binding.title.setTextColor(color)
        binding.progressView.indeterminateTintList = ColorStateList.valueOf(color)
    }

    fun setDisableBackgroundColor(@ColorInt color: Int) {
        disableBackgroundColor = color
        invalidate()
    }

    fun setDisableForegroundColor(@ColorInt color: Int) {
        disableForegroundColor = color
        invalidate()
    }

    fun setEnableColor(color: Int) {
        enableColor = color
        invalidateState()
    }

    enum class State {
        ENABLE,
        DISABLE
    }
}
