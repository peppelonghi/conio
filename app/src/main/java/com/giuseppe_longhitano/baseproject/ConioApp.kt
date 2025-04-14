package com.giuseppe_longhitano.baseproject

import android.app.Application
import com.giuseppe_longhitano.features.coins.viewModules
import com.giuseppe_longhitano.network.networkModule
import com.giuseppe_longhitano.repositories.repositoriesModule
import org.koin.core.context.GlobalContext.startKoin


class ConioApp: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(repositoriesModule + networkModule + viewModules)
        }
    }
}