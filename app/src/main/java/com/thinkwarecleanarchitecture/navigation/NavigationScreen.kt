package com.thinkwarecleanarchitecture.navigation

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
sealed class NavigationScreen(
    val route: String,
) {
    data object HomeScreenRoute : NavigationScreen("homeScreen")

    data object MovieDetailRoute : NavigationScreen("movieDetail/{movieId}") {
        fun openMovieDetail(movieId: Int) = "movieDetail/$movieId"
    }

    data object VideoFfmpegScreenRoute : NavigationScreen("videoFfmpegScreen") {
        fun openVideoFfmpegScreen() = "videoFfmpegScreen"
    }
}
