package org.tavo.project.domain.usecase.movs

class ComputeVr2UseCase {
    operator fun invoke(tov: Double, timeFactor: Double): Double =
        tov / timeFactor
}
