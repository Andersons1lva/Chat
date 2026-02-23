package dev.anderson.chatui.data

import kotlin.time.Instant

data class Message(
    val Id: Int,
    val author: String,
    val contents: String,
    val timestamp: Instant,
    val replyTo: Int? = null,
    val isPropria: Boolean
    )
