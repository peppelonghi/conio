package com.giuseppe_longhitano.baseproject

 import com.giuseppe_longhitano.baseproject.coin_details.CoindDetailsViewModel
 import com.giuseppe_longhitano.baseproject.coin_list.CoinViewModel
 import org.koin.core.module.dsl.viewModel
 import org.koin.dsl.module

val viewModules = module {
    viewModel { CoinViewModel(get()) }
    viewModel { CoindDetailsViewModel(get(), get()) }

}