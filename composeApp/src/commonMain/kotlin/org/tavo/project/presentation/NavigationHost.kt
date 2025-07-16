package org.tavo.project.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.tavo.project.presentation.screens.MainScreen
import org.tavo.project.presentation.screens.conventional.ConventionalMainScreen
import org.tavo.project.presentation.screens.conventional.sections.IsolationCoordinationScreen
import org.tavo.project.presentation.screens.conventional.sections.MovSelectionScreen
import org.tavo.project.presentation.screens.conventional.sections.SetupScreen
import org.tavo.project.presentation.screens.iec.DetailScreen

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
                        is Screen.Conventional -> ConventionalMainScreen()
                        is Screen.ItemDetail -> DetailScreen(screen.itemId)
                        is Screen.IEC -> {
                            // Handle IEC screen if needed
                        }

                        is Screen.Setup -> SetupScreen()
                        is Screen.MOVSelection -> MovSelectionScreen()
                        is Screen.IsolationCoordination -> IsolationCoordinationScreen()
                    }
                }
            }
        }
    }
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}