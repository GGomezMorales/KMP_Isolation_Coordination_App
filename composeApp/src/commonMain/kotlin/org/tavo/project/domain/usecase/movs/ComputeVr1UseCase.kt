package org.tavo.project.domain.usecase.movs

import kotlin.math.abs

class ComputeVr1UseCase {
    operator fun invoke(mcov: Double, designFactor: Double): Double =
        abs(mcov / designFactor)
}
