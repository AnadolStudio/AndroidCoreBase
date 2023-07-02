package com.anadolstudio.core.navigation

sealed class NavigationEvent

open class Back<Data>(data: Data) : NavigationEvent()

open class BackTo<Data>(data: Data) : NavigationEvent()

open class Replace<Data>(data: Data) : NavigationEvent()

open class Add<Data>(data: Data) : NavigationEvent()
