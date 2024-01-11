package com.anadolstudio.core.presentation.fragment.state_util.strategy

import android.view.View
import com.anadolstudio.core.util.common_extention.makeInvisible
import com.anadolstudio.core.presentation.fragment.state_util.strategy.base.AbstractStateChangeStrategy

open class ShowOnEnterInvisibleOnExitStrategy<T : Enum<T>> : AbstractStateChangeStrategy<T>() {

    override fun viewOnStateExit(view: View) = view.makeInvisible()

}
