package com.anadolstudio.core.presentation

import com.anadolstudio.core.navigation.NavigationEvent

interface Navigatable<NavigateData : Any> {

    fun handleNavigationEvent(event: NavigationEvent<NavigateData>)

}
