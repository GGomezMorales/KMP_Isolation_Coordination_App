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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import org.tavo.project.domain.usecase.criteria.ProtectionCapacityUseCase
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel

@Composable
fun MovSelectionScreen(
    viewModel: ConventionalMainViewModel = koinInject(),
    protectionCapacity: ProtectionCapacityUseCase = koinInject(),
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    val nextEnabled = state.surgeArrester != null &&
            state.movRatedVoltage.toDoubleOrNull() != null &&
            state.npm.toDoubleOrNull() != null &&
            state.npr.toDoubleOrNull() != null &&
            state.bilNormalized.toDoubleOrNull() != null

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
            color = MaterialTheme.colorScheme.primary,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

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
                Text(
                    "Inputs",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
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
                            modifier = Modifier.weight(0.5f)
                        )
                        // Espacio reservado para alineación
                        Spacer(modifier = Modifier.weight(0.1f))
                    }
                }

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.computeMovSelection() },
                    enabled = state.isInputValid,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Compute")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Outputs",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(8.dp))

                state.surgeArrester?.let { sa ->
                    val bilNormalized = state.bilNormalized.toDoubleOrNull() ?: 0.0
                    val nprValue = sa.npr
                    val ratio = if (nprValue != 0.0) bilNormalized / nprValue else 0.0
                    val isValid = if (bilNormalized != 0.0) {
                        protectionCapacity(bilNormalized, nprValue)
                    } else {
                        false
                    }

                    val results = listOf(
                        "Normalized BIL / NPR" to "${ratio}",
                        "Is valid for Isolation Coordination?" to if (isValid) "Yes" else "No",
                    )

                    results.forEach { (label, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = value,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                } ?: Text(
                    "Enter all the information and press \"Compute\"",
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
                enabled = nextEnabled,
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }
        }
    }
}


private fun Double?.orZero() = this ?: 0.0