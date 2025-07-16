package org.tavo.project.presentation.screens.conventional.sections

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel

@Composable
fun SetupScreen(
    viewModel: ConventionalMainViewModel = koinInject()
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Setup",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = state.maxVoltage,
            onValueChange = viewModel::onMaxVoltageChange,
            label = { Text("Vmax (kV)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { navController.navigate(Screen.Conventional) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(
                text = "Back",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
