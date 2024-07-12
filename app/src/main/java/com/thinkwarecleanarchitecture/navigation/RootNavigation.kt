@file:OptIn(ExperimentalAnimationApi::class)

package com.thinkwarecleanarchitecture.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.animation.composable
import com.tca.feature.detail.MovieDetailScreen
import com.tca.feature.home.HomeScreen
import com.tca.feature.videoffmpeg.VideoFfmpegScreen

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
@Composable
fun RootNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationScreen.HomeScreenRoute.route
    ) {
        addHomeScreen(navController)
        addMovieDetailScreen(navController)
        addVideoFfmpegScreen(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addHomeScreen(navController: NavHostController) {
    composable(NavigationScreen.HomeScreenRoute.route) {
        HomeScreen(navToMovieDetail = { movieId ->
            navController.navigate(
                NavigationScreen.MovieDetailRoute.openMovieDetail(
                    movieId
                )
            )
        })
    }
}

fun NavGraphBuilder.addMovieDetailScreen(navController: NavHostController) {
    composable(NavigationScreen.MovieDetailRoute.route) { navBackStackEntry ->
        val movieId = navBackStackEntry.arguments?.getString("movieId")?.toIntOrNull() ?: 0
        MovieDetailScreen(movieId = movieId, navBack = {
            navController.popBackStack()
        }, navToMovieFfmpeg = {
            navController.navigate(NavigationScreen.VideoFfmpegScreenRoute.openVideoFfmpegScreen())
        })
    }
}

fun NavGraphBuilder.addVideoFfmpegScreen(navController: NavHostController) {
    composable(NavigationScreen.VideoFfmpegScreenRoute.route) { navBackStackEntry ->
        VideoFfmpegScreen {
            navController.popBackStack()
        }
    }
}
