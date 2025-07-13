package org.tavo.project.domain.usecase.criteria

import kotlin.math.abs

class ProtectionCapacityUseCase {
    operator fun invoke(normalizedBIL: Double, npr: Double): Boolean =
        abs(normalizedBIL / npr) >= 1.5
}
