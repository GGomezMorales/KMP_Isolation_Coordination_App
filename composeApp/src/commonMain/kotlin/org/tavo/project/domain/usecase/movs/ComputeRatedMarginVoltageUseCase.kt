package org.tavo.project.domain.usecase.movs

import kotlin.math.abs

class ComputeRatedMarginVoltageUseCase {
    operator fun invoke(vr: Int, safetyMargin: Double): Double =
        abs(vr * (1 + safetyMargin))
}
