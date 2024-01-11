package com.anadolstudio.core.viewmodel

import androidx.lifecycle.ViewModel
import com.anadolstudio.core.navigation.NavigationEvent
import com.anadolstudio.core.presentation.event.SingleErrorSnack
import com.anadolstudio.core.presentation.event.SingleMessageToast
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.anadolstudio.core.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.core.viewmodel.livedata.onNext
import com.anadolstudio.core.viewmodel.livedata.toImmutable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CoreActionViewModel<NavigateData> : ViewModel() {

    protected val _navigationEvent = SingleLiveEvent<NavigationEvent<NavigateData>>()
    val navigation = _navigationEvent.toImmutable()

    protected val _singleEvent = SingleLiveEvent<SingleEvent>()
    val event = _singleEvent.toImmutable()

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun showError(error: Throwable) = showEvent(SingleErrorSnack.Long(error))

    protected fun showMessage(message: String) = showEvent(SingleMessageToast.Long(message))

    protected fun showEvent(event: SingleEvent) = _singleEvent.onNext(event)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun Disposable.disposeOnCleared(): Disposable = this.also(compositeDisposable::add)

}
