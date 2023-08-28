package com.anadolstudio.core.viewmodel

import androidx.lifecycle.ViewModel
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleLiveEvent
import com.anadolstudio.core.livedata.onNext
import com.anadolstudio.core.livedata.toImmutable
import com.anadolstudio.core.navigation.NavigationEvent
import com.anadolstudio.core.presentation.event.SingleErrorSnack
import com.anadolstudio.core.presentation.event.SingleMessageToast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CoreActionViewModel<NavigateData> : ViewModel() {

    protected val _navigationEvent = SingleLiveEvent<NavigationEvent<NavigateData>>()
    val navigation = _navigationEvent.toImmutable()

    protected val _singleEvent = SingleLiveEvent<SingleEvent>()
    val event = _singleEvent.toImmutable()

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun showError(error: Throwable) = _singleEvent.onNext(SingleErrorSnack.Long(error))

    protected fun showMessage(message: String) = _singleEvent.onNext(SingleMessageToast.Long(message))

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun Disposable.disposeOnCleared(): Disposable = this.also(compositeDisposable::add)

}
