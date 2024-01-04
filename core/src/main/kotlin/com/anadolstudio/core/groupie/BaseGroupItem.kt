package com.anadolstudio.core.groupie

import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem

abstract class BaseGroupItem<VB : ViewBinding>(itemId: Long, @LayoutRes private val layoutId: Int) : BindableItem<VB>(itemId) {

    override fun bind(binding: VB, position: Int, payloads: MutableList<Any>) {
        val newItem = payloads.filterIsInstance(this::class.java).firstOrNull()

        if (newItem != null) {
            bind(binding, newItem)
            onPayloadBinding(binding, newItem)
        } else {
            super.bind(binding, position, payloads)
            onSimpleBinding(binding, this)
        }
    }

    override fun bind(binding: VB, position: Int) = bind(binding, this)

    protected abstract fun bind(binding: VB, item: BaseGroupItem<VB>)

    abstract override fun equals(other: Any?): Boolean

    abstract override fun hashCode(): Int

    override fun getChangePayload(newItem: Item<*>): Any = newItem

    protected open fun onPayloadBinding(binding: VB, item: BaseGroupItem<VB>) = Unit

    protected open fun onSimpleBinding(binding: VB, item: BaseGroupItem<VB>) = Unit

    override fun getLayout(): Int = layoutId
}
