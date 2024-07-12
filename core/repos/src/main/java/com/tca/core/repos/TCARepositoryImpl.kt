package com.tca.core.repos

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrElse
import com.tca.core.model.network.Categories
import com.tca.core.model.network.Movies
import com.tca.core.network.RestTMDBService
import com.tca.core.network.config.RecommendConfigNetwork

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class TCARepositoryImpl(
    private val restTMDBService: RestTMDBService,
) : TCARepository {
    override suspend fun getMoviesRecommendations(recommendConfig: RecommendConfigNetwork): ApiResponse<Movies?> =
        restTMDBService.getMoviesRecommendations(
            movie_id = recommendConfig.movieId,
            api_key = recommendConfig.apiKey,
            page = recommendConfig.page,
        )

    override suspend fun getGender(apiKey: String?): Categories? =
        restTMDBService.getGenres(api_key = apiKey).getOrElse {
            null
        }

    override suspend fun getMoviesPopular(
        apiKey: String?,
        page: Int?,
    ): Movies? =
        restTMDBService.getMoviesPopular(api_key = apiKey, page = page).getOrElse {
            null
        }

    override suspend fun getMoviesTopRated(
        apiKey: String?,
        page: Int?,
    ): Movies? =
        restTMDBService.getMoviesTopRated(api_key = apiKey, page = page).getOrElse {
            null
        }

    override suspend fun getMoviesUpcoming(
        apiKey: String?,
        page: Int?,
    ): Movies? =
        restTMDBService.getMoviesUpcoming(api_key = apiKey, page = page).getOrElse {
            null
        }
}
