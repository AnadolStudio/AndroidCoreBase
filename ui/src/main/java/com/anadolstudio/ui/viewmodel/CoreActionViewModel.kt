package com.anadolstudio.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.anadolstudio.ui.SingleErrorSnack
import com.anadolstudio.ui.SingleMessageToast
import com.anadolstudio.ui.navigation.NavigationEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.ui.viewmodel.livedata.onNext
import com.anadolstudio.ui.viewmodel.livedata.toImmutable
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

    protected fun Disposable?.disposeOnCleared(): Disposable? = this?.also(compositeDisposable::add)

}
