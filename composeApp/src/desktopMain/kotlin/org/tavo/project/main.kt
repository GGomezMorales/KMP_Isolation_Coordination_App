package org.tavo.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.tavo.project.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Isolation Coordination",
    ) {
        App()
    }
}
