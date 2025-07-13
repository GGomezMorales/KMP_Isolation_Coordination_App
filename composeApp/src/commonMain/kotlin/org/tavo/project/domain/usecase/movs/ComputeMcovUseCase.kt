package org.tavo.project.domain.usecase.movs

import kotlin.math.abs
import kotlin.math.sqrt

class ComputeMcovUseCase {
    operator fun invoke(maxVoltage: Double): Double =
        abs(maxVoltage) / sqrt(3.0)
}
