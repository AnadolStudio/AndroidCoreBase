package com.anadolstudio.core.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.anadolstudio.core.livedata.SingleLiveEvent

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(
        viewModelFactory: ViewModelProvider.Factory
): T {
    return ViewModelProvider(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(
        viewModelFactory: ViewModelProvider.Factory,
        body: T.() -> Unit
): T {
    val vm = ViewModelProviders.of(this, viewModelFactory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
        viewModelFactory: ViewModelProvider.Factory
): T {
    return ViewModelProvider(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.obtainViewModel(
        viewModelFactory: ViewModelProvider.Factory,
        body: T.() -> Unit
): T {
    val vm = ViewModelProvider(this, viewModelFactory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : Any, reified L : LiveData<T>> Fragment.observe(
        liveData: L,
        noinline block: (T) -> Unit
) {
    liveData.observe(this.viewLifecycleOwner, Observer<T> { it?.let { block.invoke(it) } })
}

inline fun <reified L : SingleLiveEvent<Unit>> Fragment.observe(
        singleLiveEvent: L,
        noinline block: () -> Unit
) {
    singleLiveEvent.observe(this.viewLifecycleOwner, Observer { block.invoke() })
}
