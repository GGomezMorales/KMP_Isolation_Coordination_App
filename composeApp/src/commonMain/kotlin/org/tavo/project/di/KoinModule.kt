package org.tavo.project.di

import org.koin.dsl.module
import org.tavo.project.domain.model.Factor
import org.tavo.project.domain.model.SurgeArrester
import org.tavo.project.domain.model.Voltage
import org.tavo.project.domain.usecase.criteria.IsoCoordinationUseCase
import org.tavo.project.domain.usecase.criteria.ProtectionCapacityUseCase
import org.tavo.project.domain.usecase.iso_coord.ComputeBSLUseCase
import org.tavo.project.domain.usecase.iso_coord.TheoreticalBILUseCase
import org.tavo.project.domain.usecase.movs.*
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel


val appModule = module {

    // ────────────────────────────── Domain ──────────────────────────────

    single {
        Factor(
            landing = get(),
            design = get(),
            time = get(),
            ki = get(),
            k = get()
        )
    }
    single {
        SurgeArrester(
            rated = get(),
            ratedSafety = get(),
            npm = get(),
            npr = get(),
            mcov = get(),
            tov = get(),
        )
    }
    single {
        Voltage(
            nominal = get(),
            max = get()
        )
    }


    // ────────────────────────────── Use Cases ──────────────────────────────

    single { ComputeMcovUseCase() }
    single { ComputeTovUseCase() }
    single { ComputeVr1UseCase() }
    single { ComputeVr2UseCase() }
    single { SelectVrUseCase() }
    single { ComputeSafetyMarginUseCase() }
    single { ComputeRatedMarginVoltageUseCase() }

    single { IsoCoordinationUseCase() }
    single { ProtectionCapacityUseCase() }

    single { TheoreticalBILUseCase() }
    single { ComputeBSLUseCase() }


    single {
        SelectMOVUseCase(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    // ────────────────────────────── ViewModel ──────────────────────────────

    single { ConventionalMainViewModel(get()) }
}
