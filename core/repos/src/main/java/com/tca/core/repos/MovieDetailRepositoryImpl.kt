package com.tca.core.repos

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrElse
import com.tca.core.model.network.Credits
import com.tca.core.model.network.MovieDetail
import com.tca.core.model.network.Reviews
import com.tca.core.model.network.Videos
import com.tca.core.network.RestTMDBService
import com.tca.core.network.config.MovieDetailConfig

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class MovieDetailRepositoryImpl(
    private val restTMDBService: RestTMDBService,
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieDetailConfig: MovieDetailConfig): ApiResponse<MovieDetail?> =
        restTMDBService.getMovieDetails(
            movie_id = movieDetailConfig.movieId,
            api_key = movieDetailConfig.apiKey,
        )

    override suspend fun getMovieCast(
        apiKey: String,
        movieId: Int,
    ): Credits? = restTMDBService.getCredits(movie_id = movieId, api_key = apiKey).getOrElse(null)

    override suspend fun getMovieVideo(
        apiKey: String,
        movieId: Int,
    ): Videos? = restTMDBService.getVideos(movie_id = movieId, api_key = apiKey).getOrElse(null)

    override suspend fun getMovieReview(
        apiKey: String,
        movieId: Int,
    ): Reviews? =
        restTMDBService
            .getReviews(movie_id = movieId, api_key = apiKey, page = 1)
            .getOrElse(null)
}
