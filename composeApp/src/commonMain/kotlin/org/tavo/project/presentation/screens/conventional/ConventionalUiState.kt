package org.tavo.project.presentation.screens.conventional

import org.tavo.project.domain.model.SurgeArrester

data class ConventionalUiState(
//    User input ------------------------------------------
    val maxVoltage: String = "",
    val nominalVoltage: String = "",
    val landingFactor: String = "",
    val designFactor: String = "",
    val timeFactor: String = "",

    val movRatedVoltage: String = "",
    val npm: String = "",
    val npr: String = "",
    val bilNormalized: String = "",

    val ki: String = "",
    val k: String = "",

//    User output -----------------------------------------

    val mcov: String = "",
    val tov: String = "",

    val vr1: String = "",
    val vr2: String = "",
    val vr: String = "",
    val bsl: String = "",

    val ratedSafetyVoltage: String = "",
    val ratedVoltage: String = "",

    val surgeArrester: SurgeArrester? = null,
) {
    //  TODO: Add validation for the input fields
    val isInputValid: Boolean
        get() = maxVoltage.toDoubleOrNull() != null &&
                nominalVoltage.toDoubleOrNull() != null &&
                landingFactor.toDoubleOrNull() != null &&
                designFactor.toDoubleOrNull() != null &&
                timeFactor.toDoubleOrNull() != null
}