package com.anadolstudio.ui.viewmodel

import com.anadolstudio.ui.SingleErrorSnack
import com.anadolstudio.ui.SingleMessageSnack
import com.anadolstudio.ui.SingleMessageToast
import com.anadolstudio.ui.navigation.NavigationEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.ui.viewmodel.livedata.onNext
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class CoreActionViewModelDelegate<NavigateData : Any>(
        protected val navigationEvent: SingleLiveEvent<NavigationEvent<NavigateData>>,
        protected val singleEvent: SingleLiveEvent<SingleEvent>
) {
    private val todoMessages = listOf(
            "Извините, этот функционал пока не реализован \uD83D\uDE43",
            "Мы работаем над этим функционалом, следите за обновлениями \uD83D\uDC40",
            "Эта функция будет доступна в ближайшее время \uD83C\uDFC3",
    )

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun showError(error: Throwable) = showEvent(SingleErrorSnack.Long(error))

    fun showMessage(message: String) = showEvent(SingleMessageToast.Long(message))

    fun showEvent(event: SingleEvent) = singleEvent.onNext(event)

    fun showTodo(text: String? = null) {
        val message = text ?: todoMessages.random()
        singleEvent.onNext(SingleMessageSnack.Short(message))
    }

    fun Disposable?.disposeOnCleared(): Disposable? = this?.also(compositeDisposable::add)

    fun clear() {
        compositeDisposable.clear()
    }
}
