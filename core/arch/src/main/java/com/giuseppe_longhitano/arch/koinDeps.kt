package com.giuseppe_longhitano.arch

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val coreModules = module{
    factory { Dispatchers.IO }
}