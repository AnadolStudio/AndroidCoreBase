package com.anadolstudio.ui.fragment.state_util.strategy

import android.view.View
import com.anadolstudio.ui.fragment.state_util.strategy.base.AbstractStateChangeStrategy
import com.anadolstudio.utils.util.extentions.makeGone

open class ShowOnEnterGoneOnExitStrategy<T : Enum<T>> : AbstractStateChangeStrategy<T>() {

    override fun viewOnStateExit(view: View) = view.makeGone()

}
