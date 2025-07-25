package org.tavo.project.domain.usecase.movs

import org.tavo.project.domain.model.Factor
import org.tavo.project.domain.model.SurgeArrester
import org.tavo.project.domain.model.Voltage

class SelectMOVUseCase(
    private val computeMcov: ComputeMcovUseCase,
    private val computeTov: ComputeTovUseCase,
    private val computeVr1: ComputeVr1UseCase,
    private val computeVr2: ComputeVr2UseCase,
    private val selectVr: SelectVrUseCase,
    private val computeMargin: ComputeSafetyMarginUseCase,
    private val computeRatedSafety: ComputeRatedMarginVoltageUseCase,
) {
    operator fun invoke(factor: Factor, voltage: Voltage): SurgeArrester {
        val mcov: Double = computeMcov(voltage.max)
        val tov: Double = computeTov(mcov, factor.landing)
        val vr1: Double = computeVr1(mcov, factor.design)
        val vr2: Double = computeVr2(tov, factor.time)
        val vr: Int = selectVr(vr1, vr2)
        val margin: Double = computeMargin(voltage.nominal)
        val ratedSafetyVoltage: Double = computeRatedSafety(vr, margin)

        return SurgeArrester(
            rated = 1.0,
            ratedSafety = ratedSafetyVoltage,
            npm = 1.0,
            npr = 1.0,
            mcov = mcov,
            tov = tov
        )
    }
}
