package org.tavo.project.domain.usecase.iso_coord

import kotlin.math.abs

class TheoreticalBILUseCase {
    operator fun invoke(npr: Double, k1: Double): Double =
        abs(npr * k1)
}
