package org.tavo.project.di

import org.koin.dsl.module
import org.tavo.project.domain.usecase.movs.SelectMOVUseCase
import org.tavo.project.presentation.screens.conventional.ConventionalMainViewModel


val appModule = module {

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

    factory { ConventionalMainViewModel(get()) }
}
