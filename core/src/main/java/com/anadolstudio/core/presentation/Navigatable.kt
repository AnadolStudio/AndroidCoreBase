package com.anadolstudio.core.presentation

import com.anadolstudio.core.navigation.NavigationEvent

interface Navigatable {

    fun handleNavigationEvent(event: NavigationEvent)

    class Delegate() : Navigatable {

        override fun handleNavigationEvent(event: NavigationEvent) {
            TODO("Not yet implemented")
        }
    }
}
