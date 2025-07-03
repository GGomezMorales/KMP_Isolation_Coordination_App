package org.tavo.project.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen() {
    val navController = LocalNavController.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate(Screen.Settings)
        }) {
            Text("Go to Settings")
        }

        Button(onClick = {
            navController.navigate(Screen.ItemDetail("123"))
        }) {
            Text("View Item 123")
        }
    }
}

@Composable
fun DetailScreen(itemId: String) {
    val navController = LocalNavController.current

    Column {
        Text("Details for item $itemId")
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}

@Composable
fun SettingsScreen() {
    val navController = LocalNavController.current

    Column {
        Text("Settings Screen")
        Button(onClick = { navController.resetToRoot() }) {
            Text("Back to Root")
        }
    }
}
