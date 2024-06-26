package com.anadolstudio.ui.fragment.state_util.state

import android.view.View
import com.anadolstudio.ui.fragment.state_util.strategy.ShowOnEnterGoneOnExitStrategy
import com.anadolstudio.ui.fragment.state_util.strategy.base.StateChangeStrategy

data class State<T>(
        val name: T,
        val viewsGroup: List<View>,
        val strategy: StateChangeStrategy<T> = ShowOnEnterGoneOnExitStrategy()
) where T : Enum<T>
