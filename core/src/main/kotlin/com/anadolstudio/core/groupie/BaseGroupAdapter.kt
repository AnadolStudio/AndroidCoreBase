package com.anadolstudio.core.groupie

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section

open class BaseGroupAdapter(vararg sections: Section) : GroupAdapter<GroupieViewHolder>() {

    init {
        sections.forEach { add(it) }
    }
}
