package com.anadolstudio.view.coordinator

import android.os.Parcelable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewbinding.ViewBinding

typealias ChangeScrollStateListener = ((state: ScrollState) -> Unit)

interface AppBarBehaviorCallback<RestoreState : BaseSavedState, Binding : ViewBinding> {
    
    fun canDrag(binding: Binding): Boolean
    
    fun initializeViewBinding(coordinatorLayout: CoordinatorLayout): Binding

    fun onScrollStarted(binding: Binding)
    
    fun onScrolling(binding: Binding)
    
    fun onScrollStoped(binding: Binding)

    fun onFlingStarted(binding: Binding)

    fun onFling(binding: Binding)

    fun onFlingFinished(binding: Binding)

    fun onSaveState(binding: Binding, supperState: Parcelable): RestoreState

    fun onRestoreState(binding: Binding, state: RestoreState)
    
    fun setOnChangeScrollStateListener(listener: ChangeScrollStateListener?)

    fun changeOffset(binding: Binding)

}

interface BaseSavedState : Parcelable {
    val superState: Parcelable
}
