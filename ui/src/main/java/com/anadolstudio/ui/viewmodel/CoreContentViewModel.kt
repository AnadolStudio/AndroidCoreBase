package com.anadolstudio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anadolstudio.ui.viewmodel.livedata.onNext
import com.anadolstudio.ui.viewmodel.livedata.toImmutable

abstract class CoreContentViewModel<State : Any, NavigateData : Any>(
        private val initState: State
) : CoreActionViewModel<NavigateData>() {

    protected val _stateLiveData = MutableLiveData(initState)
    val stateLiveData = _stateLiveData.toImmutable()

    protected val state: State get() = _stateLiveData.value ?: initState

    protected fun updateState(forceUpdate: Boolean = false, action: State.() -> State) {
        val newState = action.invoke(state)

        if (newState != state || forceUpdate) {
            _stateLiveData.onNext(newState)
        }
    }

}
