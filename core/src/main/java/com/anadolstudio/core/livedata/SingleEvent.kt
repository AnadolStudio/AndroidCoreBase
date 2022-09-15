package com.anadolstudio.core.livedata

sealed class SingleEvent

open class SingleMessage(val message: String) : SingleEvent()

open class SingleError(val error: Throwable) : SingleEvent()

open class SingleCustomEvent : SingleEvent()
