package com.anadolstudio.ui.fragment.state_util.strategy

import android.view.View
import com.anadolstudio.utils.animation.AnimateUtil

class ShimmersGoneStrategy<T : Enum<T>>(vararg viewsWithoutAnim: View) : ShowOnEnterGoneOnExitStrategy<T>() {

    override val viewsWithoutAnim: List<View> = viewsWithoutAnim.toList()
    override val animation = AnimateUtil.blinkAnimation()

}
