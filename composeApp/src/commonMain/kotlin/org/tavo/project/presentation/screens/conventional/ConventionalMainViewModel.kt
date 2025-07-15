package org.tavo.project.presentation.screens.conventional

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tavo.project.domain.model.Factor
import org.tavo.project.domain.model.SurgeArrester
import org.tavo.project.domain.model.Voltage
import org.tavo.project.domain.usecase.movs.SelectMOVUseCase

/**
 * UI‐state holder for the conventional coordination calculator.
 */
data class ConventionalUiState(
    val maxVoltage: String = "",
    val nominalVoltage: String = "",
    val landingFactor: String = "",
    val designFactor: String = "",
    val timeFactor: String = "",
    val result: SurgeArrester? = null,
) {
    val isInputValid: Boolean get() =
        maxVoltage.toDoubleOrNull() != null &&
                nominalVoltage.toDoubleOrNull() != null &&
                landingFactor.toDoubleOrNull() != null &&
                designFactor.toDoubleOrNull() != null &&
                timeFactor.toDoubleOrNull() != null
}

class ConventionalMainViewModel(
    private val selectMOVUseCase: SelectMOVUseCase
) {
    private val _state = MutableStateFlow(ConventionalUiState())
    val state: StateFlow<ConventionalUiState> = _state.asStateFlow()

    // ────────────────────────────── Input bindings ──────────────────────────────
    fun onMaxVoltageChange(value: String) = _state.update { it.copy(maxVoltage = value) }
    fun onNominalVoltageChange(value: String) = _state.update { it.copy(nominalVoltage = value) }
    fun onLandingFactorChange(value: String) = _state.update { it.copy(landingFactor = value) }
    fun onDesignFactorChange(value: String) = _state.update { it.copy(designFactor = value) }
    fun onTimeFactorChange(value: String) = _state.update { it.copy(timeFactor = value) }

    // ───────────────────────────────  Business  ────────────────────────────────
    fun compute() {
        val current = _state.value
        if (!current.isInputValid) return

        val factor = Factor(
            landing = current.landingFactor.toDouble(),
            design = current.designFactor.toDouble(),
            time = current.timeFactor.toDouble(),
            ki = 1.0,
            k = 1.0
        )
        val voltage = Voltage(
            nominal = current.nominalVoltage.toDouble(),
            max = current.maxVoltage.toDouble()
        )

        val result = selectMOVUseCase(factor, voltage)
        _state.update { it.copy(result = result) }
    }
}
