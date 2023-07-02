package com.anadolstudio.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleLiveEvent
import com.anadolstudio.core.livedata.onNextContent
import com.anadolstudio.core.livedata.toImmutable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CoreContentViewModel<Data> : CoreActionViewModel() {

    protected val _state = MutableLiveData<BaseViewState<Data>>()
    val state = _state.toImmutable()

    protected val content: Data? get() = (_state.value as? BaseViewState.Content<Data>)?.content

    protected fun updateContent(action: Data.() -> Data) {
        val content = content ?: return // TODO проверить, что content обновляется
        _state.onNextContent(action.invoke(content))
    }

}
