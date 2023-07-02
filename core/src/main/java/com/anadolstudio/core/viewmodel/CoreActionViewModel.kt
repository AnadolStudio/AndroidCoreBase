package com.anadolstudio.core.viewmodel

import androidx.lifecycle.ViewModel
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleLiveEvent
import com.anadolstudio.core.livedata.toImmutable
import com.anadolstudio.core.navigation.NavigationEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CoreActionViewModel : ViewModel() {

    protected val _navigationEvent = SingleLiveEvent<NavigationEvent>()
    val navigation = _navigationEvent.toImmutable()

    protected val _singleEvent = SingleLiveEvent<SingleEvent>()
    val event = _singleEvent.toImmutable()

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun Disposable.disposeOnCleared(): Disposable = this.also(compositeDisposable::add)

}
