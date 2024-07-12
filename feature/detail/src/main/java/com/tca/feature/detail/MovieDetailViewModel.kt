package com.tca.feature.detail

import Constants
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tca.core.model.db.DbMovieDetail
import com.tca.core.model.mapper.MovieDetailMapper
import com.tca.core.model.network.Credits
import com.tca.core.model.network.Reviews
import com.tca.core.model.network.Videos
import com.tca.core.network.CoroutineDispatchers
import com.tca.core.network.config.MovieDetailConfig
import com.tca.core.repos.LocalDbRepository
import com.tca.core.repos.MovieDetailRepository
import com.tca.core.flow.StateModel
import com.tca.core.repos.SocketRepository
import com.tca.core.ui.convertYoutubeUrl
import com.tca.core.ui.module.MovieDetailModule
import com.tca.core.ui.network.ResultData
import kotlinx.coroutines.launch

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class MovieDetailViewModel(
    private val movieDetailRepository: MovieDetailRepository,
    private val coroutineDispatchersIml: CoroutineDispatchers,
    private val localDbRepository: LocalDbRepository,
    private val movieDetailMapper: MovieDetailMapper
) : ViewModel() {
    val movieDetailState = StateModel<DbMovieDetail?>(null)
    val movieCastState = StateModel<Credits?>(null)
    val movieVideoState = StateModel<Videos?>(null)
    val movieReviewState = StateModel<Reviews?>(null)

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            val movieDetailConfig =
                MovieDetailConfig(
                    movieId = movieId,
                    apiKey = Constants.API_KEY,
                )
            MovieDetailModule
                .provideMovieDetailModule(
                    movieDetailConfig = movieDetailConfig,
                    localDbRepository = localDbRepository,
                    movieDetailRepository = movieDetailRepository,
                    movieDetailMapper = movieDetailMapper,
                    coroutineDispatchers = coroutineDispatchersIml,
                ).collect { state ->
                    when (state) {
                        is ResultData.SuccessLocal -> {
                            movieDetailState.updateEvent(state.data)
                        }

                        is ResultData.SuccessMerged -> {
                            movieDetailState.updateEvent(state.data)
                        }

                        is ResultData.Error -> {
                        }

                        is ResultData.Loading -> {
                        }
                    }
                }
        }
    }

    fun getMovieCast(movieId: Int) {
        viewModelScope.launch(coroutineDispatchersIml.io) {
            movieCastState.updateEvent(
                movieDetailRepository.getMovieCast(
                    movieId = movieId,
                    apiKey = Constants.API_KEY,
                ),
            )
        }
    }

    fun getMovieVideo(movieId: Int) {
        viewModelScope.launch(coroutineDispatchersIml.io) {
            movieVideoState.updateEvent(
                movieDetailRepository.getMovieVideo(
                    movieId = movieId,
                    apiKey = Constants.API_KEY,
                ),
            )
        }
    }

    fun getMovieReview(movieId: Int) {
        viewModelScope.launch(coroutineDispatchersIml.io) {
            val data =
                movieDetailRepository.getMovieReview(movieId = movieId, apiKey = Constants.API_KEY)
            movieReviewState.updateEvent(data)
        }
    }

    fun openYoutubeApp(
        context: Context,
        keyYoutube: String,
    ) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.google.android.youtube")
        intent.data = Uri.parse(keyYoutube.convertYoutubeUrl())
        ContextCompat.startActivity(context, intent, null)
    }
}
