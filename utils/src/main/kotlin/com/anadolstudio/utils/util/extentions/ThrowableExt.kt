package com.anadolstudio.utils.util.extentions

fun <T : Any> tryOrNull(action: () -> T): T? = try {
    action.invoke()
} catch (ex: Throwable) {
    null
}
