package com.giuseppe_longhitano.repositories

import com.giuseppe_longhitano.domain.repositories.CoinRepository
import org.koin.dsl.module


val repositoriesModule = module {
    single<CoinRepository> { CoinRepositoryImpl(get()) }
}



