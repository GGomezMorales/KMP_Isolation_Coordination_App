package org.tavo.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.tavo.project.presentation.NavigationHost
import org.tavo.project.presentation.rememberNavController

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavigationHost(navController = navController)
    }
}
