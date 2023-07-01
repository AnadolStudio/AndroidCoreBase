package com.anadolstudio.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleLiveEvent
import com.anadolstudio.core.livedata.onNextContent
import com.anadolstudio.core.livedata.toImmutable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<Data> : ViewModel() {

    protected val _singleEvent = SingleLiveEvent<SingleEvent>()
    val event = _singleEvent.toImmutable()

    protected val _state = MutableLiveData<BaseViewState<Data>>()
    val state = _state.toImmutable()

    private val compositeDisposable by lazy { CompositeDisposable() }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun getContentOrNull(): Data? = (_state.value as? BaseViewState.Content<Data>)?.content

    protected fun updateContent(action: Data.() -> Data) {
        val content = getContentOrNull() ?: return
        _state.onNextContent(action.invoke(content))
    }

    protected fun Disposable.disposeOnViewModelDestroy(): Disposable {
        compositeDisposable.add(this)
        return this
    }
}
