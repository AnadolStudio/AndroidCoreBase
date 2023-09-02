package com.anadolstudio.core.presentation.adapters.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: List<Fragment> = emptyList()
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragments: MutableList<Fragment> = fragments.toMutableList()
        private set

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

    fun add(index: Int, fragment: Fragment) {
        if (fragments.getOrNull(index) == null) {
            fragments.add(index, fragment)
            notifyItemChanged(index)
        }
    }

    override fun getItemId(position: Int): Long = fragments[position].hashCode().toLong()

    override fun containsItem(itemId: Long): Boolean = fragments.find { it.hashCode().toLong() == itemId } != null
}
