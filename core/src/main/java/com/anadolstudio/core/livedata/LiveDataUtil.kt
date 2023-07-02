package com.anadolstudio.core.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.anadolstudio.core.viewmodel.Lce

fun <T> MutableLiveData<T>.toImmutable() = this as LiveData<T>

fun <T> MutableLiveData<T>.onNext(t: T) {
    this.value = t
}

fun <T> MutableLiveData<Lce<T>>.onNextContent(t: T) {
    this.value = Lce.Content.Data(t)
}
