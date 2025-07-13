package org.tavo.project.domain.usecase.movs

import kotlin.math.abs
import kotlin.math.roundToInt

class ComputeRatedMarginVoltageUseCase {
    operator fun invoke(vr: Int, safetyMargin: Double): Int =
        abs(vr * (1 + safetyMargin).roundToInt())
}
