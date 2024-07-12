package com.thinkwarecleanarchitecture.di

import com.tca.core.repos.LocalDbRepository
import com.tca.core.repos.LocalDbRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
val databaseModule =
    module {
        singleOf(::LocalDbRepositoryImpl) bind LocalDbRepository::class
    }
