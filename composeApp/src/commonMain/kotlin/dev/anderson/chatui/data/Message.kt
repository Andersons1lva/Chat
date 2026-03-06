package dev.anderson.chatui.data

import kotlin.time.Clock
import kotlin.time.Instant



data class Message(
    val id: Int = 0,
    val author: String = "",
    val contents: String = "",
    val timestamp: Instant = Clock.System.now(),
    val replyTo: Int? = null,
    val isPropria: Boolean = false
)
