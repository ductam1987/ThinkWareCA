package com.thinkwarecleanarchitecture.di

import com.tca.core.repos.MovieDetailRepository
import com.tca.core.repos.MovieDetailRepositoryImpl
import com.tca.core.repos.SocketRepository
import com.tca.core.repos.SocketRepositoryImpl
import com.tca.core.repos.TCARepository
import com.tca.core.repos.TCARepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

val repositoryModule =
    module {
        singleOf(::TCARepositoryImpl) { bind<TCARepository>() }
        singleOf(::MovieDetailRepositoryImpl) { bind<MovieDetailRepository>() }
        singleOf(::SocketRepositoryImpl) { bind<SocketRepository>() }
    }
