package com.tca.core.repos

import com.skydoves.sandwich.ApiResponse
import com.tca.core.model.network.Categories
import com.tca.core.model.network.Movies
import com.tca.core.network.config.RecommendConfigNetwork

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

interface TCARepository {
    suspend fun getMoviesRecommendations(recommendConfig: RecommendConfigNetwork): ApiResponse<Movies?>

    suspend fun getGender(apiKey: String?): Categories?

    suspend fun getMoviesPopular(
        apiKey: String?,
        page: Int?,
    ): Movies?

    suspend fun getMoviesTopRated(
        apiKey: String?,
        page: Int?,
    ): Movies?

    suspend fun getMoviesUpcoming(
        apiKey: String?,
        page: Int?,
    ): Movies?
}
