package com.anadolstudio.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager

abstract class CoreDialogFragment(@LayoutRes private val layoutId: Int) : AppCompatDialogFragment(layoutId) {

    abstract fun getDialogTag(): String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
    }

    override fun onStart() {
        super.onStart()
        with(dialog) {
            // Для корректной отрисовки диалога
            this?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            this?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun show(fragmentManager: FragmentManager) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Remove duplicate
        fragmentManager.findFragmentByTag(getDialogTag())?.let(fragmentTransaction::remove)

        this.show(fragmentTransaction, getDialogTag())
    }

    private fun setTransparentStatusBar() {
        dialog?.window?.apply {
            decorView.systemUiVisibility = decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

}
