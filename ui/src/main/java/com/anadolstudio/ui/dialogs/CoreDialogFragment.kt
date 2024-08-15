package com.anadolstudio.ui.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.FragmentManager

abstract class CoreDialogFragment(@LayoutRes private val layoutId: Int) : AppCompatDialogFragment(layoutId) {

    protected open var isStatusBarByNightMode: Boolean = true

    abstract fun getDialogTag(): String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = onBackPressed()
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    abstract fun onBackPressed()

    override fun onStart() {
        super.onStart()
        with(dialog) {
            // Для корректной отрисовки диалога
            this?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            this?.window?.let { setupBackgroundColor(it) }
        }
        updateStatusBarIcons()
    }

    protected open fun setupBackgroundColor(window: Window) {
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun updateStatusBarIcons() {
        if (isStatusBarByNightMode) {
            setDarkStatusBarIcon()
        } else {
            setLightStatusBarIcon()
        }
    }

    protected fun setDarkStatusBarIcon() = setStatusBarIconColor(isDark = true)

    protected fun setLightStatusBarIcon() = setStatusBarIconColor(isDark = false)

    private fun setStatusBarIconColor(isDark: Boolean) {
        val window = getWindow() ?: return
        val decorView = getDecorView() ?: return

        val insetsController = WindowInsetsControllerCompat(window, decorView)
        insetsController.isAppearanceLightStatusBars = isDark
    }

    protected fun getWindow(): Window? = requireDialog().window

    protected fun getDecorView(): View? = getWindow()?.decorView

    fun show(fragmentManager: FragmentManager) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Remove duplicate
        fragmentManager.findFragmentByTag(getDialogTag())?.let(fragmentTransaction::remove)

        this.show(fragmentTransaction, getDialogTag())
    }

}
