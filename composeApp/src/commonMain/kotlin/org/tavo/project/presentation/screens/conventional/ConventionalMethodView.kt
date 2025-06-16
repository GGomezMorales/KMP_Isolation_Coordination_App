package org.tavo.project.presentation.screens.conventional

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.tavo.project.domain.Factor
import org.tavo.project.domain.Voltage
import org.tavo.project.presentation.screens.conventional.adapter.FactorTable
import org.tavo.project.presentation.screens.conventional.adapter.VoltageTable

@Composable
fun ConventionalMethodView() {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        FactorTable(
            modifier = Modifier.fillMaxWidth(),
            headers = listOf("Factor", "Value"),
            rows = listOf(
                Factor("Landing Factor", value = .15),
                Factor("Design Factor", value = .15),
                Factor("Time Factor", value = .20)
            )
        )

        Spacer(Modifier.height(24.dp))

        VoltageTable(
            modifier = Modifier.fillMaxWidth(),
            headers = listOf("Variable", "Symbol", "Value"),
            rows = listOf(
                Voltage("Nominal Voltage", "V_nom", 110.0),
                Voltage("Max. Voltage", "V_max", 123.0),
                Voltage("Max. Cont. Oper. Voltage", "MCOV", 115.0),
                Voltage("Temporary Overvoltage", "TOV", 130.0)
            )
        )
    }
}