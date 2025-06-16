package org.tavo.project.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
inline fun <reified T : Any> viewModelFactory(
    crossinline factory: () -> T
): T {
    return remember { factory() }
}
