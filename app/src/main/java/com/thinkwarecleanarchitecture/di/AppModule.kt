package com.thinkwarecleanarchitecture.di

import com.tca.core.network.CoroutineDispatchers
import com.tca.core.network.DefaultCoroutineDispatchers
import com.tca.core.network.di.networkModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

val appModule =
    module {
        singleOf(::DefaultCoroutineDispatchers) bind CoroutineDispatchers::class
    }

val allModules =
    module {
        includes(
            appModule,
            networkModule,
            viewModelModule,
            repositoryModule,
            databaseModule,
            mapperModule,
        )
    }
