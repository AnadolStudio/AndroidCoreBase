package com.anadolstudio.core.presentation.fragment

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.anadolstudio.core.util.night_mode.getCurrentNightMode

abstract class CoreFragment(@LayoutRes private val layoutId: Int) : Fragment(layoutId) {

    protected open var isDarkStatusBarIcons: Boolean = false
    protected open var isStatusBarByNightMode: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (isStatusBarByNightMode) {
            isDarkStatusBarIcons = getCurrentNightMode(resources) == UiModeManager.MODE_NIGHT_NO
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Исправление проблемы с fitsSystemWindow.
         * При использовании fragments иногда происходит ошибка в расчётах fitsSystemWindow.
         * Эта строчка вызывает перерасчёт insets.
         *
         * Подробности тут: https://medium.com/androiddevelopers/windows-insets-fragment-transitions-9024b239a436
         */
        ViewCompat.requestApplyInsets(view)

        updateStatusBarIcons()
    }

    protected fun changeStatusBarIconsColor(isDarkStatusBarIcons: Boolean) {
        this.isDarkStatusBarIcons = isDarkStatusBarIcons
        updateStatusBarIcons()
    }

    private fun updateStatusBarIcons() {
        if (isDarkStatusBarIcons) {
            setDarkStatusBarIcon()
        } else {
            setLightStatusBarIcon()
        }
    }

    protected fun setDarkStatusBarIcon() = setStatusBarIconColor(isDark = true)

    protected fun setLightStatusBarIcon() = setStatusBarIconColor(isDark = false)

    private fun setStatusBarIconColor(isDark: Boolean) {
        val insetsController = WindowCompat.getInsetsController(
                requireActivity().window,
                requireActivity().window.decorView
        )
        insetsController.isAppearanceLightStatusBars = isDark
    }

    protected fun initFragmentResultListeners(vararg requestKeys: String) = requestKeys.forEach { key ->
        setFragmentResultListener(childFragmentManager, key)
        setFragmentResultListener(parentFragmentManager, key)
    }

    protected fun setFragmentResultListener(fragmentManager: FragmentManager, key: String) {
        fragmentManager.setFragmentResultListener(key, this) { requestKey: String, data: Bundle ->
            handleFragmentResult(requestKey, data)
        }
    }

    protected open fun handleFragmentResult(requestKey: String, data: Bundle) = Unit
}
