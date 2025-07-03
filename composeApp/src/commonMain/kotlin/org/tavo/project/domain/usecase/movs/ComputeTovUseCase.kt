package org.tavo.project.domain.usecase.movs

class ComputeTovUseCase {
    operator fun invoke(mcov: Double, landingFactor: Double): Double =
        mcov * landingFactor
}
