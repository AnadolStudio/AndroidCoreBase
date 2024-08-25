package com.anadolstudio.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.anadolstudio.ui.navigation.NavigationEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.ui.viewmodel.livedata.onNext

class CoreContentViewModelDelegate<State : Any, NavigateData : Any>(
        private val initState: State,
        private val stateLiveData: MutableLiveData<State>,
        navigationEvent: SingleLiveEvent<NavigationEvent<NavigateData>>,
        singleEvent: SingleLiveEvent<SingleEvent>
) : CoreActionViewModelDelegate<NavigateData>(navigationEvent, singleEvent) {

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
