package com.anadolstudio.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.anadolstudio.ui.navigation.NavigationEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.ui.viewmodel.livedata.toImmutable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CoreActionViewModel<NavigateData : Any> : ViewModel() {

    protected val _navigationEvent = SingleLiveEvent<NavigationEvent<NavigateData>>()
    val navigation = _navigationEvent.toImmutable()

    protected val _singleEvent = SingleLiveEvent<SingleEvent>()
    val event = _singleEvent.toImmutable()

    protected val delegateList = mutableListOf<CoreActionViewModelDelegate<NavigateData>>()

    protected open val baseDelegate: CoreActionViewModelDelegate<NavigateData> = CoreActionViewModelDelegate(
            navigationEvent = _navigationEvent,
            singleEvent = _singleEvent,
    )

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun showError(error: Throwable) = baseDelegate.showError(error)

    protected fun showMessage(message: String) = baseDelegate.showMessage(message)

    protected fun showEvent(event: SingleEvent) = baseDelegate.showEvent(event)

    protected fun showTodo(text: String? = null) = baseDelegate.showTodo(text)

    open fun onStop() = Unit

    open fun onStart() = Unit

    open fun onPause() = Unit

    open fun onResume() = Unit

    override fun onCleared() {
        compositeDisposable.clear()
        baseDelegate.clear()
        delegateList.forEach { it.clear() }

        super.onCleared()
    }

    protected fun Disposable?.disposeOnCleared(): Disposable? = this?.also(compositeDisposable::add)

    protected inline fun <reified T : CoreActionViewModelDelegate<NavigateData>> T.registerDelegate(): T =
            this.also(delegateList::add)

}
