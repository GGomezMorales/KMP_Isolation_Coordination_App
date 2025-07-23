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
import org.tavo.project.domain.usecase.movs.*
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.Screen
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel
import kotlin.math.roundToInt

@Composable
fun SetupScreen(
    viewModel: ConventionalMainViewModel = koinInject(),
    computeVr1: ComputeVr1UseCase = koinInject(),
    computeVr2: ComputeVr2UseCase = koinInject(),
    selectVr: SelectVrUseCase = koinInject(),
    computeMargin: ComputeSafetyMarginUseCase = koinInject(),
    computeRatedSafety: ComputeRatedMarginVoltageUseCase = koinInject()
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
            text = "Setup",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = MaterialTheme.shapes.large
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Inputs", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(8.dp))

                val inputs = listOf(
                    "Nomanal Voltage" to state.nominalVoltage,
                    "Max. Voltage" to state.maxVoltage,
                    "Landing Factor" to state.landingFactor,
                    "Design Factor" to state.designFactor,
                    "Time Factor" to state.timeFactor,
                    "Ki Factor" to state.ki,
                    "K Factor" to state.k
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
                            onValueChange = { new ->
                                when (label) {
                                    "Nomanal Voltage" -> viewModel.onNominalVoltageChange(new)
                                    "Max. Voltage" -> viewModel.onMaxVoltageChange(new)
                                    "Landing Factor" -> viewModel.onLandingFactorChange(new)
                                    "Design Factor" -> viewModel.onDesignFactorChange(new)
                                    "Time Factor" -> viewModel.onTimeFactorChange(new)
                                    "Ki Factor" -> viewModel.onKiChange(new)
                                    "K Factor" -> viewModel.onKChange(new)
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.computeSetup() },
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
                Text("Outputs", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(8.dp))

                state.surgeArrester?.let { surgeArrester ->
                    val mcov: Double = surgeArrester.mcov
                    val tov: Double = surgeArrester.tov
                    val vr1: Double = computeVr1(mcov, state.designFactor.toDouble())
                    val vr2: Double = computeVr2(tov, state.timeFactor.toDouble())
                    val vr: Int = selectVr(vr1, vr2)
                    val margin: Double = computeMargin(state.nominalVoltage.toDouble())
                    val ratedSafety: Double = computeRatedSafety(vr, margin)

                    val results = listOf(
                        "MCOV" to "${mcov} kV",
                        "TOV" to "${tov} kV",
                        "Vr1" to "${vr1} kV",
                        "Vr2" to "${vr2} kV",
                        "Vr" to "${vr} kV",
                        "Nominal Voltage (Suggested)" to "${ratedSafety.roundToInt()} kV"
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
                    "Introduce todos los datos y pulsa \"Calcular\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // ────────────────────────── Navigation ──────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { navController.navigate(Screen.Conventional) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }
            Button(
                onClick = { navController.navigate(Screen.MOVSelection) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }
        }
    }
}

private fun Double?.orZero() = this ?: 0.0