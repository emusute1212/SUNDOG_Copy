package org.sundog.copy.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.sundog.copy.viewModel.SettingPageViewModel
import org.sundog.copy.viewModel.TopPageViewModel

val TopPageViewModelModule = module {
    viewModel {
        TopPageViewModel(get())
    }
}

val SettingsViewModelModule = module {
    viewModel {
        SettingPageViewModel(get())
    }
}