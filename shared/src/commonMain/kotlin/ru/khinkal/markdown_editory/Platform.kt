package ru.khinkal.markdown_editory

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform