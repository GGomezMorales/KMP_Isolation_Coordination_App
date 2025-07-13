package org.tavo.project.domain.usecase.criteria

import kotlin.math.abs

class IsoCoordinationUseCase {
    operator fun invoke(bsl: Double, npm: Double): Double =
        abs(bsl / npm)
}
