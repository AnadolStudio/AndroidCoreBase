package com.anadolstudio.ui.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

class FragmentViewBindingFactory<T : ViewBinding>(
        fragment: Fragment,
        viewBindingFactory: (View) -> T
) : ViewDependingPropertyFactory<T>(fragment, viewBindingFactory)

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
        FragmentViewBindingFactory(this, viewBindingFactory)
