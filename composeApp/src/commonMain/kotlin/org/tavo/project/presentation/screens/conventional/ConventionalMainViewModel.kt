package org.tavo.project.presentation.screens.conventional

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.tavo.project.domain.model.Factor
import org.tavo.project.domain.model.Voltage
import org.tavo.project.domain.usecase.movs.SelectMOVUseCase

class ConventionalMainViewModel(
    private val selectMOVUseCase: SelectMOVUseCase
) {
    private val _state = MutableStateFlow(ConventionalUiState())
    val state: StateFlow<ConventionalUiState> = _state.asStateFlow()

    // ──────────────────── Input bindings ────────────────────
    fun onNominalVoltageChange(value: String) = _state.update { it.copy(nominalVoltage = value) }
    fun onMaxVoltageChange(value: String) = _state.update { it.copy(maxVoltage = value) }
    fun onLandingFactorChange(value: String) = _state.update { it.copy(landingFactor = value) }
    fun onDesignFactorChange(value: String) = _state.update { it.copy(designFactor = value) }
    fun onTimeFactorChange(value: String) = _state.update { it.copy(timeFactor = value) }
    fun onMovRatedVoltageChange(value: String) = _state.update { it.copy(movRatedVoltage = value) }
    fun onNpmChange(value: String) = _state.update { it.copy(npm = value) }
    fun onNprChange(value: String) = _state.update { it.copy(npr = value) }
    fun onBilNormalizedChange(value: String) = _state.update { it.copy(bilNormalized = value) }
    fun onKiChange(value: String) = _state.update { it.copy(ki = value) }
    fun onKChange(value: String) = _state.update { it.copy(k = value) }

    fun computeSetup() {
        val current = _state.value
        if (!current.isInputValid) return

        val factor = Factor(
            landing = current.landingFactor.toDouble(),
            design = current.designFactor.toDouble(),
            time = current.timeFactor.toDouble(),
            ki = current.ki.toDouble(),
            k = current.k.toDouble()
        )

        val voltage = Voltage(
            nominal = current.nominalVoltage.toDouble(),
            max = current.maxVoltage.toDouble()
        )

        val surgeArrester = selectMOVUseCase(factor, voltage)
        _state.update { it.copy(surgeArrester = surgeArrester) }
    }

    fun computeMovSelection() {
        val current = _state.value
        val setup = current.surgeArrester ?: return
        val movNominal = current.movRatedVoltage.toDoubleOrNull() ?: return
        val npmVal = current.npm.toDoubleOrNull() ?: return
        val nprVal = current.npr.toDoubleOrNull() ?: return
        val bilVal = current.bilNormalized.toDoubleOrNull() ?: return

        val factor = Factor(
            landing = current.landingFactor.toDouble(),
            design = current.designFactor.toDouble(),
            time = current.timeFactor.toDouble(),
            ki = current.ki.toDouble(),
            k = current.k.toDouble()
        )
        val voltage = Voltage(nominal = movNominal, max = setup.mcov)

        var surgeArrester = selectMOVUseCase(factor, voltage)
        surgeArrester = surgeArrester.copy(npm = npmVal, npr = nprVal)
        _state.update { it.copy(surgeArrester = surgeArrester, bilNormalized = bilVal.toString()) }
    }

    fun computeIsolationCoordination() {
        val current = _state.value
        val setup = current.surgeArrester ?: return
        // Validar inputs de aislamiento
        if (current.landingFactor.toDoubleOrNull() == null) return
        if (current.designFactor.toDoubleOrNull() == null) return
        if (current.timeFactor.toDoubleOrNull() == null) return
        if (current.ki.toDoubleOrNull() == null) return
        if (current.k.toDoubleOrNull() == null) return

        val factor = Factor(
            landing = current.landingFactor.toDouble(),
            design = current.designFactor.toDouble(),
            time = current.timeFactor.toDouble(),
            ki = current.ki.toDouble(),
            k = current.k.toDouble()
        )
        // Para aislamiento usamos MCOV y TOV de Setup como rango
        val voltage = Voltage(nominal = setup.mcov, max = setup.tov)
        val result = selectMOVUseCase(factor, voltage)
        _state.update { it.copy(surgeArrester = result) }
    }
}
