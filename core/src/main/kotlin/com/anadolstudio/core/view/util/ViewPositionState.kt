package com.anadolstudio.core.view.util

import android.view.View

class ViewPositionState(
        private val translationX: Float,
        private val translationY: Float,
        private val scaleX: Float,
        private val scaleY: Float,
        private val rotation: Float
) {
    constructor(view: View) : this(
            translationX = view.translationX,
            translationY = view.translationX,
            scaleX = view.scaleX,
            scaleY = view.scaleY,
            rotation = view.rotation,
    )

    fun rebootToDefault(view: View) {
        view.translationX = translationX
        view.translationY = translationY
        view.scaleX = scaleX
        view.scaleY = scaleY
        view.rotation = rotation
    }

    fun rebootToDefaultWithAnim(view: View) {
        view.animate()
                .translationX(translationX)
                .translationY(translationY)
                .scaleX(scaleX)
                .scaleY(scaleY)
                .rotation(rotation)

        view.clearAnimation()
    }

    fun rebootToDefaultTranslation(view: View) {
        view.translationX = translationX
        view.translationY = translationY
    }
}
