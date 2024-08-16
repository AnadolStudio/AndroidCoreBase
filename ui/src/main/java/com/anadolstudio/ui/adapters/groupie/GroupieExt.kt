package com.anadolstudio.ui.adapters.groupie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Group
import com.xwray.groupie.Section
import timber.log.Timber

inline fun <reified T> Section.getItemByClass(position: Int = 0): T? =
        when {
            itemCount > position -> getItem(position) as? T
            else -> null
        }

fun <T : Group> Section.update(vararg t: T) {
    this.update(t.toList())
}

fun Section.safeClear(recyclerView: RecyclerView) {
    try {
        this.clear()
    } catch (ex: IllegalStateException) {
        Timber.d(ex)
        recyclerView.post { this.clear() }
    }
}

inline fun <reified T : Group> Section.safeUpdate(recyclerView: View, items: List<T>) {
    safeUpdate(recyclerView, *items.toTypedArray())
}

fun <T : Group> Section.safeUpdate(recyclerView: View, vararg t: T) {
    try {
        this.update(t.toList())
    } catch (ex: IllegalStateException) {
        Timber.d(ex)
        recyclerView.post { this.update(t.toList()) }
    }
}
