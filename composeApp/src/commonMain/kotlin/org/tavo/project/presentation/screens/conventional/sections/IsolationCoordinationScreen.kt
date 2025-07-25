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
import org.tavo.project.domain.usecase.criteria.IsoCoordinationUseCase
import org.tavo.project.domain.usecase.iso_coord.ComputeBSLUseCase
import org.tavo.project.domain.usecase.iso_coord.TheoreticalBILUseCase
import org.tavo.project.presentation.LocalNavController
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel


@Composable
fun IsolationCoordinationScreen(
    viewModel: ConventionalMainViewModel = koinInject(),
    theoreticalBILUseCase: TheoreticalBILUseCase = koinInject(),
    computeBSLUseCase: ComputeBSLUseCase = koinInject(),
    isoCoordinationUseCase: IsoCoordinationUseCase = koinInject(),
) {
    val navController = LocalNavController.current
    val state by viewModel.state.collectAsState()

    // Validación para habilitar el botón de cálculo
    val canCompute = state.isInputValid && state.surgeArrester != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Coordinación de Aislamiento",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        // Botón de cálculo (no hay formulario de entrada aquí)
        Button(
            onClick = { viewModel.computeIsolationCoordination() },
            enabled = canCompute,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Calcular")
        }

        // Tarjeta de resultados
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
                    // Convertimos ki y k a Double
                    val ki = state.ki.toDoubleOrNull() ?: 0.0
                    val k = state.k.toDoubleOrNull() ?: 0.0
                    // Cálculos mediante casos de uso
                    val bilTeorico = theoreticalBILUseCase(sa.npr, ki)
                    val bsl = computeBSLUseCase(bilTeorico, k)
                    val isoRatio = isoCoordinationUseCase(bsl, sa.npm)

                    // Puedes crear tu propia clasificación según isoRatio si lo deseas
                    val clase = when {
                        isoRatio >= 1.5 -> "Apto"
                        isoRatio >= 1.0 -> "Condicional"
                        else -> "No apto"
                    }

                    val results = listOf(
                        "BIL teórico (NPR × Ki)" to "${bilTeorico} kV",
                        "BSL (BIL teórico × K)" to "${bsl} kV",
                        "Relación BSL / NPM" to isoRatio.toString(),
                        "Clase" to clase
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
                    "Introduce los datos en las vistas anteriores y pulsa \"Calcular\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Navegación
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
        }
    }
}

private fun Double?.orZero() = this ?: 0.0