package org.tavo.project.domain.usecase.movs

class ComputeSafetyMarginUseCase {
    operator fun invoke(nominalVoltage: Double): Double =
        if (nominalVoltage > 100.0) 0.05 else 0.10
}
