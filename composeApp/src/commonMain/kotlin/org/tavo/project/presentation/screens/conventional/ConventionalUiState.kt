package org.tavo.project.presentation.screens.conventional

import org.tavo.project.domain.model.SurgeArrester

data class ConventionalUiState(
//    User input ------------------------------------------
    val maxVoltage: String = "",
    val nominalVoltage: String = "",
    val landingFactor: String = "",
    val designFactor: String = "0.8",
    val timeFactor: String = "1.1",

    val movRatedVoltage: String = "",
    val npm: String = "",
    val npr: String = "",
    val bilNormalized: String = "",

    val ki: String = "1.25",
    val k: String = "0.8",

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
    //  TODO: Add validation for the input fields. It's possible to separate this logic into a UseCase or Validator class.
    val isInputValid: Boolean
        get() = maxVoltage.toDoubleOrNull() != null &&
                nominalVoltage.toDoubleOrNull() != null &&
                landingFactor.toDoubleOrNull() != null &&
                designFactor.toDoubleOrNull() != null &&
                timeFactor.toDoubleOrNull() != null
}