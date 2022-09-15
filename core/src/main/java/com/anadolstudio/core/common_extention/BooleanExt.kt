package com.anadolstudio.core.common_extention

fun Boolean.onTrue(action: () -> Unit) = this.also { isTrue -> if (isTrue) action.invoke() }

fun Boolean.onFalse(action: () -> Unit) = this.also { isTrue -> if (!isTrue) action.invoke() }
