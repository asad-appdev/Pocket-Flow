package com.xasdify.pocketflow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform