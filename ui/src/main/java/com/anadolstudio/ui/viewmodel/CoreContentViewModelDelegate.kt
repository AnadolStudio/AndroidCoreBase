package com.anadolstudio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.ui.viewmodel.livedata.onNext

class CoreContentViewModelDelegate<State : Any>(
        private val initState: State,
        private val stateLiveData: MutableLiveData<State>,
        singleEvent: SingleLiveEvent<SingleEvent>
) : CoreActionViewModelDelegate(singleEvent) {

    val state: State get() = stateLiveData.value ?: initState

    fun updateState(forceUpdate: Boolean = false, action: State.() -> State) {
        val newState = action.invoke(state)

        if (newState != state || forceUpdate) {
            stateLiveData.onNext(newState)
        }
    }

    fun postUpdateState(forceUpdate: Boolean = false, action: State.() -> State) {
        val newState = action.invoke(state)

        if (newState != state || forceUpdate) {
            stateLiveData.postValue(newState)
        }
    }
}
