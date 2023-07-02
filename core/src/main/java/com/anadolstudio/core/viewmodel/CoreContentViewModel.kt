package com.anadolstudio.core.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.livedata.onNextContent
import com.anadolstudio.core.livedata.toImmutable

abstract class CoreContentViewModel<State : Any>(initState: State) : CoreActionViewModel() {

    /**
     * Поле для сохранения данных стейта, чтобы обойти ограничение, когда Loading/Error затирают стейт Content
      */
    protected val state = MutableLiveData(initState)

    protected val _viewState = MutableLiveData<Lce<State>>(Lce.Loading())
    val viewState = _viewState.toImmutable()

    protected fun updateState(action: State.() -> State) {
        val previousData = state.value ?: return
        val newData = action.invoke(previousData)

        if (newData != previousData) {
            state.onNext(newData)
            _viewState.onNextContent(newData)
        }
    }

    protected fun updateViewState(action: Lce<State>.() -> Lce<State>) {
        val previousState = _viewState.value ?: Lce.Loading()
        val newState = action.invoke(previousState)

        if (newState != previousState) {
            _viewState.onNext(newState)
        }
    }
}
