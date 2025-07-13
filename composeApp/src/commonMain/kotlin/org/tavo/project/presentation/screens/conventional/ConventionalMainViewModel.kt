package org.tavo.project.presentation.screens.conventional

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.tavo.project.domain.model.Factor
import org.tavo.project.domain.model.SurgeArrester
import org.tavo.project.domain.model.Voltage
import org.tavo.project.domain.usecase.movs.SelectMOVUseCase

class ConventionalMainViewModel(
    private val selectMOVUseCase: SelectMOVUseCase
) {
    var maxVoltage by mutableStateOf("")
        private set
    var nominalVoltage by mutableStateOf("")
        private set
    var landingFactor by mutableStateOf("")
        private set
    var designFactor by mutableStateOf("")
        private set
    var timeFactor by mutableStateOf("")
        private set

    var result by mutableStateOf<SurgeArrester?>(null)
        private set

    fun onMaxVoltageChanged(value: String) {
        maxVoltage = value
    }

    fun onNominalVoltageChanged(value: String) {
        nominalVoltage = value
    }

    fun onLandingFactorChanged(value: String) {
        landingFactor = value
    }

    fun onDesignFactorChanged(value: String) {
        designFactor = value
    }

    fun onTimeFactorChanged(value: String) {
        timeFactor = value
    }

    fun compute() {
        val maxV = maxVoltage.toDoubleOrNull() ?: return
        val nomV = nominalVoltage.toDoubleOrNull() ?: return
        val landing = landingFactor.toDoubleOrNull() ?: return
        val design = designFactor.toDoubleOrNull() ?: return
        val time = timeFactor.toDoubleOrNull() ?: return

        val factor = Factor(landing = landing, design = design, time = time, ki = 1.0, k = 1.0)
        val voltage = Voltage(nominal = nomV, max = maxV)
        result = selectMOVUseCase.invoke(factor, voltage)
    }
}
