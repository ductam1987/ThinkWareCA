package com.thinkwarecleanarchitecture.di

import com.tca.core.model.mapper.MovieDetailMapper
import com.tca.core.model.mapper.MoviesMapper
import com.tca.core.model.mapper.RecommendationMapper
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
@OptIn(ExperimentalSerializationApi::class)
val mapperModule =
    module {
        single {
            Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                explicitNulls = true
                prettyPrint = true
                isLenient = true
                coerceInputValues = true
            }
        }

        factoryOf(::RecommendationMapper)
        factoryOf(::MoviesMapper)
        factoryOf(::MovieDetailMapper)
    }
