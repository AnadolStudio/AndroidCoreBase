package com.anadolstudio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anadolstudio.ui.viewmodel.livedata.toImmutable

abstract class CoreContentViewModel<State : Any, NavigateData : Any>(
        protected val initState: State
) : CoreActionViewModel<NavigateData>() {

    protected val _stateLiveData = MutableLiveData(initState)
    val stateLiveData = _stateLiveData.toImmutable()

    override val baseDelegate = CoreContentViewModelDelegate<State>(
            initState = initState,
            stateLiveData = _stateLiveData,
            singleEvent = _singleEvent,
    )

    protected val state: State get() = baseDelegate.state

    protected fun updateState(forceUpdate: Boolean = false, action: State.() -> State) =
            baseDelegate.updateState(forceUpdate, action)

    protected fun postUpdateState(forceUpdate: Boolean = false, action: State.() -> State) =
            baseDelegate.postUpdateState(forceUpdate, action)

}
