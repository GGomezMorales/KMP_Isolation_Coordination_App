package org.tavo.project.domain.usecase.movs

import kotlin.math.abs

class ComputeVr2UseCase {
    operator fun invoke(tov: Double, timeFactor: Double): Double =
        abs(tov / timeFactor)
}
