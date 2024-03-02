package com.anadolstudio.ui.viewmodel.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anadolstudio.ui.viewmodel.LceState

fun <T> MutableLiveData<T>.toImmutable() = this as LiveData<T>

fun <T> MutableLiveData<T>.onNext(t: T) {
    this.value = t
}

fun <T> MutableLiveData<LceState<T>>.onNextContent(t: T) {
    this.value = LceState.Content(t)
}
