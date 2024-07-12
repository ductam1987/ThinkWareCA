package com.tca.feature.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tca.feature.detail.view.BoxRateView
import com.tca.feature.detail.view.MovieCastView
import com.tca.feature.detail.view.MovieDetailBody
import com.tca.feature.detail.view.MovieReviewView
import com.tca.feature.detail.view.MovieVideoView
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Composable
fun MovieDetailScreen(
    movieDetailViewModel: MovieDetailViewModel = koinViewModel(),
    movieId: Int,
    navBack: () -> Unit,
    navToMovieFfmpeg: () -> Unit,
) {
    val context = LocalContext.current

    val movieDetail = movieDetailViewModel.movieDetailState.collectAsState()
    val movieCast = movieDetailViewModel.movieCastState.collectAsState()
    val movieVideos = movieDetailViewModel.movieVideoState.collectAsState()
    val movieReviews = movieDetailViewModel.movieReviewState.collectAsState()

    /**
     * LaunchedEffect: Shouldn't call multiple functions inside LaunchEffect.
     * Each function should be run independently and is not affected by the other.
     * This provides better isolation and control over the execution flow.
     */

    LaunchedEffect(Unit) {
        movieDetailViewModel.getMovieDetail(movieId = movieId)
    }

    LaunchedEffect(Unit) {
        movieDetailViewModel.getMovieCast(movieId = movieId)
    }

    LaunchedEffect(Unit) {
        movieDetailViewModel.getMovieVideo(movieId = movieId)
    }

    LaunchedEffect(Unit) {
        movieDetailViewModel.getMovieReview(movieId = movieId)
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        movieDetail.value?.let { _movieDetail ->
            MovieDetailBody(_movieDetail,
                navBack = {
                    navBack.invoke()
                },
                navToMovieFfmpeg = {
                    navToMovieFfmpeg.invoke()
                })
        }

        BoxRateView()

        MovieCastView(movieCast.value?.cast ?: emptyList())

        MovieVideoView(listVideos = movieVideos.value?.results ?: emptyList()) { key ->
            movieDetailViewModel.openYoutubeApp(context = context, keyYoutube = key)
        }

        MovieReviewView(movieReviews.value?.results ?: emptyList())
    }
}
