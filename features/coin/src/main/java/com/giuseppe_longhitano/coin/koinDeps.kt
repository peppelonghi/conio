package com.giuseppe_longhitano.coin


import com.giuseppe_longhitano.coin.coin_details.screen.CoinDetailsViewModel
import com.giuseppe_longhitano.coin.coin_list.screen.CoinListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureCoinModules = module {

    viewModelOf(::CoinListViewModel)

    viewModelOf(::CoinDetailsViewModel)

}