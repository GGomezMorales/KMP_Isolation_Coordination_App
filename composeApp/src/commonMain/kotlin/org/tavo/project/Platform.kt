package org.tavo.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform