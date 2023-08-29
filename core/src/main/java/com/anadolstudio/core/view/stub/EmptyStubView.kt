package com.anadolstudio.core.view.stub

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.core.R
import com.anadolstudio.core.util.common.dpToPx
import com.anadolstudio.core.databinding.ViewEmpyStubBinding

class EmptyStubView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private companion object {
         val DEFAULT_SIDE = 100F.dpToPx()
    }

    private val binding: ViewEmpyStubBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_empy_stub, this)
        binding = ViewEmpyStubBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.EmptyStubView, defStyleAttr, 0) {
            setTitle(getString(R.styleable.EmptyStubView_title))
            setDescription(getString(R.styleable.EmptyStubView_description))
            setImage(getDrawable(R.styleable.EmptyStubView_src))
            setImageSize(getDimension(R.styleable.EmptyStubView_srcSide, DEFAULT_SIDE))
            setTitleAppearance(getResourceId(R.styleable.EmptyStubView_titleTextAppearance, 0))
            setDescriptionAppearance(getResourceId(R.styleable.EmptyStubView_descriptionTextAppearance, 0))
        }
    }

    fun setImage(drawable: Drawable?) {
        binding.emptyStubImage.setImageDrawable(drawable)
    }

    fun setImageSize(sidePx: Float) {
        binding.emptyStubImage.layoutParams.height = sidePx.toInt()
        binding.emptyStubImage.layoutParams.width = sidePx.toInt()
        binding.emptyStubImage.requestLayout()
    }

    fun setTitle(string: String?) {
        binding.emptyStubTitle.text = string
    }

    fun setDescription(string: String?) {
        binding.emptyStubDescription.text = string
    }

    fun setTitleAppearance(id: Int) {
        binding.emptyStubTitle.setTextAppearance(id)
    }

    fun setDescriptionAppearance(id: Int) {
        binding.emptyStubDescription.setTextAppearance(id)
    }

}
