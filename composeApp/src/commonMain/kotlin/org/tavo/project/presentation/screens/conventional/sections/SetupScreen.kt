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
fun SetupScreen(
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
            text = "Setup",
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Entradas", style = MaterialTheme.typography.titleMedium)

                Spacer(Modifier.height(8.dp))

                val inputs = listOf(
                    "Vmáx (IEC)" to state.maxVoltage,
                    "FA (aterriz.)" to state.landingFactor,
                    "Fd (diseño)" to state.designFactor,
                    "FT (tiempo)" to state.timeFactor,
                    "Ki (IEC)" to state.ki,
                    "K (adicional)" to state.k
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
                                    "Vmáx (IEC)" -> viewModel.onMaxVoltageChange(new)
                                    "FA (aterriz.)" -> viewModel.onLandingFactorChange(new)
                                    "Fd (diseño)" -> viewModel.onDesignFactorChange(new)
                                    "FT (tiempo)" -> viewModel.onTimeFactorChange(new)
                                    "Ki (IEC)" -> viewModel.onKiChange(new)
                                    "K (adicional)" -> viewModel.onKChange(new)
                                }
                            },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.compute() },
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
                        "MCOV = Vmáx/√3" to (sa.mcov.format(2) + " kV"),
                        "TOV = MCOV·FA" to (sa.tov.format(2) + " kV"),
                        "Vr1 = MCOV/Fd" to (vr1.format(2) + " kV"),
                        "Vr2 = TOV/Ft" to (vr2.format(2) + " kV"),
                        "Vr (mayor)" to (vr.format(2) + " kV"),
                        "Margen ${marginPct}%" to (vn.format(2) + " kV")
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

        // ────────────────────────── Navegación ──────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Atrás")
            }
            Button(
                onClick = { navController.navigate(Screen.MOVSelection) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Siguiente")
            }
        }
    }
}

// Helpers
private fun Double?.orZero() = this ?: 0.0
private fun Double.format(decimals: Int) = "%.${decimals}f".format(this)
