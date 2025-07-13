package org.tavo.project.domain.usecase.iso_coord

import kotlin.math.abs

class ComputeBSLUseCase {
    operator fun invoke(normalizedBIL: Double, k: Double): Double =
        abs(normalizedBIL * k)
}