package org.tavo.project.presentation.screens.conventional.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel

@Composable
fun MovSelectionScreen(
    viewModel: ConventionalMainViewModel = koinInject()
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "MOV Selection",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // ────────────────────────── Inputs ──────────────────────────
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Entradas MOV", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                val inputs = listOf(
                    "MOV Rated Voltage" to state.movRatedVoltage,
                    "NPM" to state.npm,
                    "NPR" to state.npr,
                    "Normalized BIL" to state.bilNormalized
                )

                inputs.forEach { (label, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = label,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        OutlinedTextField(
                            value = value,
                            onValueChange = {
                                when (label) {
                                    "MOV Rated Voltage" -> viewModel.onMovRatedVoltageChange(it)
                                    "NPM" -> viewModel.onNpmChange(it)
                                    "NPR" -> viewModel.onNprChange(it)
                                    "Normalized BIL" -> viewModel.onBilNormalizedChange(it)
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.computeMovSelection() },
                    enabled = state.isInputValid,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Calcular")
                }
            }
        }

        // ────────────────────────── Resultados ──────────────────────────
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Resultados", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(8.dp))

                state.surgeArrester?.let { sa ->
                    val vr1 = sa.mcov / state.designFactor.toDoubleOrNull().orZero()
                    val vr2 = sa.tov / state.timeFactor.toDoubleOrNull().orZero()
                    val vr = maxOf(vr1, vr2)
                    val marginPct = if (vr > 100) 5 else 10
                    val vn = vr * (1 + marginPct / 100.0)

                    val results = listOf(
                        "MCOV" to "${sa.mcov} kV",
                        "TOV" to "${sa.tov} kV",
                        "Vr1" to "${vr1} kV",
                        "Vr2" to "${vr2} kV",
                        "Vr (mayor)" to "${vr} kV",
                        "Margen ${marginPct}%" to "${vn} kV"
                    )

                    results.forEach { (label, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(label, style = MaterialTheme.typography.bodyMedium)
                            Text(value, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                } ?: Text(
                    "Introduce los datos y pulsa \"Calcular\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // ────────────────────────── Navegación ──────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { navController.navigate(Screen.Setup) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }
            Button(
                onClick = { navController.navigate(Screen.IsolationCoordination) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }
        }
    }
}

// Helpers
private fun Double?.orZero() = this ?: 0.0