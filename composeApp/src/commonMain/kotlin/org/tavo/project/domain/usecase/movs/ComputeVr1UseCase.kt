package org.tavo.project.domain.usecase.movs

class ComputeVr1UseCase {
    operator fun invoke(mcov: Double, designFactor: Double): Double =
        mcov / designFactor
}
