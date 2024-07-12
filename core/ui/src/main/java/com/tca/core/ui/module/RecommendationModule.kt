package com.tca.core.ui.module

import com.skydoves.sandwich.ApiResponse
import com.tca.core.model.db.SubDbMovie
import com.tca.core.model.mapper.MoviesMapper
import com.tca.core.model.mapper.RecommendationMapper
import com.tca.core.model.network.Movies
import com.tca.core.network.CoroutineDispatchers
import com.tca.core.network.config.RecommendConfigDb
import com.tca.core.network.config.RecommendConfigNetwork
import com.tca.core.repos.LocalDbRepository
import com.tca.core.repos.TCARepository
import com.tca.core.ui.network.NetworkBoundRepository
import com.tca.core.ui.network.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
object RecommendationModule {
    suspend fun provideRecommendationModule(
        recommendConfig: RecommendConfigNetwork,
        localDbRepository: LocalDbRepository,
        tmdbRepository: TCARepository,
        recommendationMapper: RecommendationMapper,
        moviesMapper: MoviesMapper,
        coroutineDispatchers: CoroutineDispatchers,
    ): Flow<ResultData<SubDbMovie>> =
        object : NetworkBoundRepository<SubDbMovie, Movies?>() {
            override suspend fun saveRemoteData(response: Movies?) {
                response?.results?.let { listMovie ->
                    localDbRepository.saveMovies(moviesMapper.mapToEntity(response))
                    localDbRepository.saveListMovie(recommendationMapper.mapToListEntity(listMovie))
                }
            }

            override suspend fun fetchFromLocal(): Flow<SubDbMovie> =
                flow {
                    val recommendConfigDb =
                        RecommendConfigDb(
                            offset = (
                                if (recommendConfig.page == 1) {
                                    0L
                                } else {
                                    (recommendConfig.page.minus(1)).times(20L)
                                }
                            ),
                            limit = 20,
                        )
                    emit(
                        SubDbMovie(
                            totalResults = localDbRepository.getMovies()?.totalResult,
                            listDbMovies = localDbRepository.getListMovie(recommendConfigDb),
                        ),
                    )
                }

            override suspend fun fetchFromRemote(): ApiResponse<Movies?> = tmdbRepository.getMoviesRecommendations(recommendConfig)
        }.asFlow().flowOn(coroutineDispatchers.io)
}
