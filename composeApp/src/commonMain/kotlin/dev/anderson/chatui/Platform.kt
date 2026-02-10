package dev.anderson.chatui

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform