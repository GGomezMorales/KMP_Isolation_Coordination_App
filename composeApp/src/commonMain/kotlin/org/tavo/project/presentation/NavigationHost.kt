package org.tavo.project.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun rememberNavController(startDestination: Screen = Screen.Main): NavController {
    return remember { NavController(startDestination) }
}

@Composable
fun NavigationHost(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val currentScreen by navController.backStack.collectAsState()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        Box(modifier) {
            currentScreen.forEachIndexed { index, screen ->
                if (index == currentScreen.lastIndex) {
                    when (screen) {
                        is Screen.Main -> MainScreen()
                        is Screen.Settings -> SettingsScreen()
                        is Screen.ItemDetail -> DetailScreen(screen.itemId)
                        Screen.Detail -> TODO()
                    }
                }
            }
        }
    }
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}