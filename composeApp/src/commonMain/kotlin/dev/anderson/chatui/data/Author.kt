package dev.anderson.chatui.data

import kotlin.time.Instant

data class Author(val name: String, val lastMessage: String, val time: Instant)
