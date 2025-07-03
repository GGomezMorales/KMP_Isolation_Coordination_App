package org.tavo.project.domain.usecase.movs

import kotlin.math.sqrt

class ComputeMcovUseCase {
    operator fun invoke(maxVoltage: Double): Double =
        maxVoltage / sqrt(3.0)
}
