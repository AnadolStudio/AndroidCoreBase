package com.anadolstudio.ui

import com.anadolstudio.ui.navigation.NavigationEvent

interface Navigatable<NavigateData : Any> {

    fun handleNavigationEvent(event: NavigationEvent<NavigateData>)

}
