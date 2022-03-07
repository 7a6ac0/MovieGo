package tabacowang.me.moviego.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tabacowang.me.moviego.ui.all.MovieAllViewModel
import tabacowang.me.moviego.ui.detail.MovieDetailViewModel
import tabacowang.me.moviego.ui.home.HomeViewModel

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { MovieDetailViewModel() }
    viewModel { MovieAllViewModel() }
}