package org.tavo.project.domain.usecase.movs

import kotlin.math.max
import kotlin.math.roundToInt

class SelectVrUseCase {
    operator fun invoke(vr1: Double, vr2: Double): Int =
        max(vr1.roundToInt(), vr2.roundToInt())
}
