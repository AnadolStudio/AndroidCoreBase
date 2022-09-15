package com.anadolstudio.core.viewmodel

import androidx.lifecycle.ViewModel
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleLiveEvent
import com.anadolstudio.core.livedata.toImmutable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    protected val _singleEvent = SingleLiveEvent<SingleEvent>()
    val event = _singleEvent.toImmutable()

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun Disposable.disposeOnViewModelDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}
