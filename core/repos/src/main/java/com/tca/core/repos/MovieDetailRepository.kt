package com.tca.core.repos

import com.skydoves.sandwich.ApiResponse
import com.tca.core.model.network.Credits
import com.tca.core.model.network.MovieDetail
import com.tca.core.model.network.Reviews
import com.tca.core.model.network.Videos
import com.tca.core.network.config.MovieDetailConfig

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
interface MovieDetailRepository {
    suspend fun getMovieDetail(movieDetailConfig: MovieDetailConfig): ApiResponse<MovieDetail?>

    suspend fun getMovieCast(
        apiKey: String,
        movieId: Int,
    ): Credits?

    suspend fun getMovieVideo(
        apiKey: String,
        movieId: Int,
    ): Videos?

    suspend fun getMovieReview(
        apiKey: String,
        movieId: Int,
    ): Reviews?
}
