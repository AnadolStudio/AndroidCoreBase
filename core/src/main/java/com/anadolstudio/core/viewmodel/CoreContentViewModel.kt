package com.anadolstudio.core.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.livedata.toImmutable
import com.anadolstudio.core.presentation.ContentableState

abstract class CoreContentViewModel<State : ContentableState, NavigateData : Any>(
        private val initState: State
) : CoreActionViewModel<NavigateData>() {

    protected val _stateLiveData = MutableLiveData(initState)
    val stateLiveData = _stateLiveData.toImmutable()

    protected val state: State get() = _stateLiveData.value ?: initState

    protected fun updateState(action: State.() -> State) {
        val newState = action.invoke(state)

        if (newState != state) {
            _stateLiveData.onNext(newState)
        }
    }

}
