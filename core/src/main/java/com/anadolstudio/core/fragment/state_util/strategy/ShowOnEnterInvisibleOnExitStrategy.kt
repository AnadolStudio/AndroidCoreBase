package com.anadolstudio.core.fragment.state_util.strategy

import android.view.View
import com.anadolstudio.core.common_extention.makeInvisible
import com.anadolstudio.core.fragment.state_util.strategy.base.AbstractStateChangeStrategy

open class ShowOnEnterInvisibleOnExitStrategy<T : Enum<T>> : AbstractStateChangeStrategy<T>() {

    override fun viewOnStateExit(view: View) = view.makeInvisible()

}
