package com.ab.notesappkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform