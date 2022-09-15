package com.anadolstudio.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class CoreBaseFragment<ViewState>(@LayoutRes private val layoutId: Int) : Fragment() {

    abstract fun render(state: ViewState)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId, container, false)
}
