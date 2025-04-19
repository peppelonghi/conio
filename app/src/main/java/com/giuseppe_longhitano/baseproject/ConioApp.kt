package com.giuseppe_longhitano.baseproject

import android.app.Application
import com.giuseppe_longhitano.coin.featureCoinModules
import com.giuseppe_longhitano.network.networkModule
import com.giuseppe_longhitano.repositories.repositoriesModule
import org.koin.core.context.GlobalContext.startKoin


class ConioApp: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules( repositoriesModule + networkModule + featureCoinModules)
        }
    }
}