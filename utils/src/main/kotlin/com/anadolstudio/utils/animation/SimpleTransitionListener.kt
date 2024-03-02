package com.anadolstudio.utils.animation

import androidx.transition.Transition

class SimpleTransitionListener(
        private val onStart: (Transition.TransitionListener, Transition) -> Unit = { _, _ -> },
        private val onEnd: (Transition.TransitionListener, Transition) -> Unit = { _, _ -> },
        private val onCancel: (Transition.TransitionListener, Transition) -> Unit = { _, _ -> },
        private val onPause: (Transition.TransitionListener, Transition) -> Unit = { _, _ -> },
        private val onResume: (Transition.TransitionListener, Transition) -> Unit = { _, _ -> },
) : Transition.TransitionListener {

    override fun onTransitionStart(transition: Transition) = onStart.invoke(this, transition)
    override fun onTransitionEnd(transition: Transition) = onEnd.invoke(this, transition)
    override fun onTransitionCancel(transition: Transition) = onCancel.invoke(this, transition)
    override fun onTransitionPause(transition: Transition) = onPause.invoke(this, transition)
    override fun onTransitionResume(transition: Transition) = onResume.invoke(this, transition)

}
