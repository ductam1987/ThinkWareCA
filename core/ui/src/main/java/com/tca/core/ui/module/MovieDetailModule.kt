package com.tca.core.ui.module

import com.skydoves.sandwich.ApiResponse
import com.tca.core.model.db.DbMovieDetail
import com.tca.core.model.mapper.MovieDetailMapper
import com.tca.core.model.network.MovieDetail
import com.tca.core.network.CoroutineDispatchers
import com.tca.core.network.config.MovieDetailConfig
import com.tca.core.repos.LocalDbRepository
import com.tca.core.repos.MovieDetailRepository
import com.tca.core.ui.network.NetworkBoundRepository
import com.tca.core.ui.network.ResultData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
object MovieDetailModule {
    suspend fun provideMovieDetailModule(
        movieDetailConfig: MovieDetailConfig,
        localDbRepository: LocalDbRepository,
        movieDetailRepository: MovieDetailRepository,
        movieDetailMapper: MovieDetailMapper,
        coroutineDispatchers: CoroutineDispatchers,
    ): Flow<ResultData<DbMovieDetail?>> =
        object : NetworkBoundRepository<DbMovieDetail?, MovieDetail?>() {
            override suspend fun saveRemoteData(response: MovieDetail?) {
                response?.let { movieDetail ->
                    localDbRepository.saveMovieDetail(movieDetailMapper.mapToEntity(movieDetail))
                }
            }

            override suspend fun fetchFromLocal(): Flow<DbMovieDetail?> =
                flow {
                    emit(localDbRepository.getMovieDetail(movieDetailConfig.movieId.toLong()))
                }

            override suspend fun fetchFromRemote(): ApiResponse<MovieDetail?> = movieDetailRepository.getMovieDetail(movieDetailConfig)
        }.asFlow().flowOn(coroutineDispatchers.io)
}
