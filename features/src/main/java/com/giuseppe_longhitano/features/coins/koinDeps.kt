package com.giuseppe_longhitano.features.coins


 import com.giuseppe_longhitano.features.coins.coin_details.CoinDetailsViewModel
 import com.giuseppe_longhitano.features.coins.coin_list.CoinsListViewModel
 import org.koin.core.module.dsl.viewModel
 import org.koin.dsl.module

val viewModules = module {
    viewModel { CoinsListViewModel(get()) }
    viewModel { CoinDetailsViewModel(get(), get()) }

}