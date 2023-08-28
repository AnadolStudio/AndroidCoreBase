package com.anadolstudio.core.navigation

sealed class NavigationEvent<Data>

open class Back<Data>(val data: Data? = null) : NavigationEvent<Data>()
open class BackTo<Data>(val data: Data) : NavigationEvent<Data>()
open class Replace<Data>(val data: Data) : NavigationEvent<Data>()
open class Add<Data>(val data: Data) : NavigationEvent<Data>()
