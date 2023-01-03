package com.anadolstudio.core.common_extention

fun <T : Any> tryOrNull(action: () -> T): T? = try {
    action.invoke()
} catch (ex: Throwable) {
    null
}
