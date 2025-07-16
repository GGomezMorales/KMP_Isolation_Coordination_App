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
fun IsolationCoordinationScreen(
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
            text = "Isolation Coordination",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = state.landingFactor,
            onValueChange = viewModel::onLandingFactorChange,
            label = { Text("Landing Factor") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.designFactor,
            onValueChange = viewModel::onDesignFactorChange,
            label = { Text("Design Factor") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.timeFactor,
            onValueChange = viewModel::onTimeFactorChange,
            label = { Text("Time Factor") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.ki,
            onValueChange = viewModel::onKiChange,
            label = { Text("Ki") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.k,
            onValueChange = viewModel::onKChange,
            label = { Text("K") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.compute() },
            enabled = state.isInputValid,
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
                text = "Calculate",
                style = MaterialTheme.typography.titleMedium
            )
        }

        state.surgeArrester?.let { result ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Result",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text("MCOV: ${result.mcov}")
            Text("TOV: ${result.tov}")
            Text("Vr (safety): ${result.ratedSafety}")
        }

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
