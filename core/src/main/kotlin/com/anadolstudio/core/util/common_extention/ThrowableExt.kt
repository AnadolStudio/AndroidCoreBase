package com.anadolstudio.core.util.common_extention

fun <T : Any> tryOrNull(action: () -> T): T? = try {
    action.invoke()
} catch (ex: Throwable) {
    null
}
