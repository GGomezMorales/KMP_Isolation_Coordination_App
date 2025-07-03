package org.tavo.project.domain.usecase.movs

class ComputeRatedMarginVoltageUseCase {
    operator fun invoke(vr: Int, safetyMargin: Double): Double =
        vr * (1 + safetyMargin)
}
