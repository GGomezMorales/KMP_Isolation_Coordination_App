package org.tavo.project.domain.usecase.movs

import kotlin.math.abs

class ComputeTovUseCase {
    operator fun invoke(mcov: Double, landingFactor: Double): Double =
        abs(mcov * landingFactor)
}
