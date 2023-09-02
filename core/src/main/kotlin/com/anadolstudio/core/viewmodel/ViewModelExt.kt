package com.anadolstudio.core.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anadolstudio.core.viewmodel.livedata.SingleLiveEvent

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(
        viewModelFactory: ViewModelProvider.Factory
): T {
    return ViewModelProvider(this, viewModelFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.obtainViewModel(
        viewModelFactory: ViewModelProvider.Factory,
        body: T.() -> Unit
): T {
    val vm = ViewModelProvider(this, viewModelFactory)[T::class.java]
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

fun <T : Any, L : LiveData<T>> Fragment.observe(
        liveData: L,
        block: (T) -> Unit
) {
    liveData.observe(this.viewLifecycleOwner) { it.let { block.invoke(it) } }
}

fun <L : SingleLiveEvent<Unit>> Fragment.observe(
        singleLiveEvent: L,
        block: () -> Unit
) {
    singleLiveEvent.observe(this.viewLifecycleOwner) { block.invoke() }
}

fun <T : Any, L : LiveData<T>> AppCompatActivity.observe(
        liveData: L,
        block: (T) -> Unit
) {
    liveData.observe(this) { it.let { block.invoke(it) } }
}

fun <L : SingleLiveEvent<Unit>> AppCompatActivity.observe(
        singleLiveEvent: L,
        block: () -> Unit
) {
    singleLiveEvent.observe(this) { block.invoke() }
}
