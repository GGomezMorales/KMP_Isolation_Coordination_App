package org.tavo.project.presentation.screens.conventional

// conventional/ConventionalMethodViewModel.kt
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.tavo.project.domain.Factor
import org.tavo.project.domain.Voltage

class ConventionalMethodViewModel {
    var factors by mutableStateOf(
        listOf(
            Factor("Landing Factor", value = .15),
            Factor("Design Factor", value = .15),
            Factor("Time Factor", value = .20)
        )
    )
        private set

    var voltages by mutableStateOf(
        listOf(
            Voltage("Nominal Voltage", "V_nom", 110.0),
            Voltage("Max. Voltage", "V_max", 123.0),
            Voltage("Max. Cont. Oper. Voltage", "MCOV", 115.0),
            Voltage("Temporary Overvoltage", "TOV", 130.0)
        )
    )
        private set

    fun updateFactor(index: Int, newValue: Double) {
        factors = factors.mapIndexed { i, factor ->
            if (i == index) factor.copy(value = newValue) else factor
        }
    }

    fun updateVoltage(index: Int, newValue: Double) {
        voltages = voltages.mapIndexed { i, voltage ->
            if (i == index) voltage.copy(value = newValue) else voltage
        }
    }
}
