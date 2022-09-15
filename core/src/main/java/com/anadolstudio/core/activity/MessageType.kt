package com.anadolstudio.core.activity

sealed class MessageType(val text: String) {

    class Toast(text: String) : MessageType(text)

    class Snack(text: String) : MessageType(text)
}
