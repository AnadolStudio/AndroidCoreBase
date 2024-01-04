package com.anadolstudio.core.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
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

inline fun <reified VM : ViewModel> Fragment.assistedViewModel(
        crossinline creator: (SavedStateHandle) -> VM,
): Lazy<VM> = viewModels { createAbstractSavedStateViewModelFactory(arguments, creator) }

inline fun <reified T : ViewModel> SavedStateRegistryOwner.createAbstractSavedStateViewModelFactory(
        arguments: Bundle? = Bundle(),
        crossinline creator: (SavedStateHandle) -> T,
): ViewModelProvider.Factory {
    return object : AbstractSavedStateViewModelFactory(
            owner = this@createAbstractSavedStateViewModelFactory,
            defaultArgs = arguments,
    ) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle,
        ): T = creator(handle) as T
    }
}
