package org.tavo.project.presentation.screens.conventional

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@Composable
fun ConventionalMainScreen(
    viewModel: ConventionalMainViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // ─────────────────────────── Input fields ───────────────────────────
        OutlinedTextField(
            value = state.maxVoltage,
            onValueChange = viewModel::onMaxVoltageChange,
            label = { Text("Vmax (kV)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.nominalVoltage,
            onValueChange = viewModel::onNominalVoltageChange,
            label = { Text("Vnom (kV)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.landingFactor,
            onValueChange = viewModel::onLandingFactorChange,
            label = { Text("Factor aterrizaje") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.designFactor,
            onValueChange = viewModel::onDesignFactorChange,
            label = { Text("Factor diseño") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = state.timeFactor,
            onValueChange = viewModel::onTimeFactorChange,
            label = { Text("Factor tiempo") },
            modifier = Modifier.fillMaxWidth()
        )

        // ───────────────────────────── Actions ──────────────────────────────
        Button(
            onClick = viewModel::compute,
            enabled = state.isInputValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        // ──────────────────────────── Result UI ─────────────────────────────
        state.result?.let { result ->
            Spacer(Modifier.height(16.dp))
            Text("Resultado", style = MaterialTheme.typography.titleMedium)
            Text("MCOV: ${'$'}{result.mcov}")
            Text("TOV: ${'$'}{result.tov}")
            Text("Vr (seguridad): ${'$'}{result.ratedSafety}")
        }
    }
}
