package com.tca.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tca.core.model.db.DbMovie
import com.tca.core.model.network.Genre
import com.tca.core.model.network.Movie
import com.tca.core.ui.DevicePreviews
import com.tca.core.ui.dataTest.HomeFakeDataPreviewUI
import com.tca.core.ui.dataTest.HomePreviewParameterProvider
import com.tca.feature.home.view.CategoryView
import com.tca.feature.home.view.PopularView
import com.tca.feature.home.view.RecommendationView
import com.tca.feature.home.view.TopRatedView
import com.tca.feature.home.view.UpcomingView
import org.koin.androidx.compose.koinViewModel
import theme.colorCrimson

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Composable
fun HomeScreen(
    recommendViewModel: RecommendViewModel = koinViewModel(),
    navToMovieDetail: (Int) -> Unit,
) {
    val context = LocalContext.current

    /**
     * Movie Recommend
     */
    val moviesRecommendation = remember { mutableStateListOf<DbMovie>() }
    val totalMoviesRecommend: MutableState<Int> = remember { mutableIntStateOf(0) }
    val pagingMoviesRecommend = remember { mutableIntStateOf(1) }

    /**
     * Movie Gender
     */
    val genreList = recommendViewModel.genre.collectAsState()

    /**
     * Movie Popular
     */
    val moviesPopular = remember { mutableStateListOf<Movie>() }
    val totalMoviesPopular: MutableState<Int> = remember { mutableIntStateOf(0) }
    val pagingMoviesPopular = remember { mutableIntStateOf(1) }

    /**
     * Movie Top Rated
     */
    val moviesTopRated = remember { mutableStateListOf<Movie>() }
    val totalMoviesTopRated: MutableState<Int> = remember { mutableIntStateOf(0) }
    val pagingMoviesTopRated = remember { mutableIntStateOf(1) }

    /**
     * Movie Upcoming
     */
    val moviesUpcoming = remember { mutableStateListOf<Movie>() }
    val totalMoviesUpcoming: MutableState<Int> = remember { mutableIntStateOf(0) }
    val pagingMoviesUpcoming = remember { mutableIntStateOf(1) }

    /**
     * LaunchedEffect: Shouldn't call multiple functions inside LaunchEffect.
     * Each function should be run independently and is not affected by the other.
     * This provides better isolation and control over the execution flow.
     */

    LaunchedEffect(Unit) {
        recommendViewModel.getTMDBRecommend(paging = pagingMoviesRecommend.intValue) { _movies ->
            if (_movies?.listDbMovies?.isNotEmpty() == true) {
                moviesRecommendation.addAll(_movies.listDbMovies ?: emptyList())
                totalMoviesRecommend.value = _movies.totalResults ?: 0
            }
        }
    }

    LaunchedEffect(Unit) {
        recommendViewModel.getTMDBGender()
    }

    LaunchedEffect(Unit) {
        recommendViewModel.getTMDBPopular(paging = pagingMoviesPopular.intValue) { _movies ->
            if (_movies?.results?.isNotEmpty() == true) {
                moviesPopular.addAll(_movies.results)
                totalMoviesPopular.value = _movies.total_results
            }
        }
    }

    LaunchedEffect(Unit) {
        recommendViewModel.getTMDBTopRated(paging = pagingMoviesTopRated.intValue) { _movies ->
            if (_movies?.results?.isNotEmpty() == true) {
                moviesTopRated.addAll(_movies.results)
                totalMoviesTopRated.value = _movies.total_results
            }
        }
    }

    LaunchedEffect(Unit) {
        recommendViewModel.getTMDBUpcoming(paging = pagingMoviesUpcoming.intValue) { _movies ->
            if (_movies?.results?.isNotEmpty() == true) {
                moviesUpcoming.addAll(_movies.results)
                totalMoviesUpcoming.value = _movies.total_results
            }
        }
    }

    HomeScreenDetail(
        totalMoviesRecommend = totalMoviesRecommend,
        moviesRecommendation = moviesRecommendation,
        genreList = genreList.value?.genres ?: emptyList(),
        totalMoviesPopular = totalMoviesPopular,
        moviesPopular = moviesPopular,
        totalMoviesTopRated = totalMoviesTopRated,
        moviesTopRated = moviesTopRated,
        totalMoviesUpcoming = totalMoviesUpcoming,
        moviesUpcoming = moviesUpcoming,
        loadMoreRecommendation = {
            pagingMoviesRecommend.intValue += 1
            recommendViewModel.getTMDBRecommend(paging = pagingMoviesRecommend.intValue) { _movies ->
                if (_movies?.listDbMovies?.isNotEmpty() == true) {
                    moviesRecommendation.addAll(_movies.listDbMovies!!)
                }
            }
        },
        loadMorePopular = {
            pagingMoviesPopular.intValue += 1
            recommendViewModel.getTMDBPopular(paging = pagingMoviesPopular.intValue) { _movies ->
                if (_movies?.results?.isNotEmpty() == true) {
                    moviesPopular.addAll(_movies.results)
                }
            }
        },
        loadMoreTopRated = {
            pagingMoviesTopRated.intValue += 1
            recommendViewModel.getTMDBTopRated(paging = pagingMoviesTopRated.intValue) { _movies ->
                if (_movies?.results?.isNotEmpty() == true) {
                    moviesTopRated.addAll(_movies.results)
                }
            }
        },
        loadMoreUpcoming = {
            pagingMoviesUpcoming.intValue += 1
            recommendViewModel.getTMDBUpcoming(paging = pagingMoviesUpcoming.intValue) { _movies ->
                if (_movies?.results?.isNotEmpty() == true) {
                    moviesUpcoming.addAll(_movies.results)
                }
            }
        },
        navToMovieDetail = navToMovieDetail,
    )
}

@Composable
fun HomeScreenDetail(
    totalMoviesRecommend: MutableState<Int>,
    moviesRecommendation: List<DbMovie>,
    genreList: List<Genre>? = emptyList(),
    totalMoviesPopular: MutableState<Int>,
    moviesPopular: List<Movie>,
    totalMoviesTopRated: MutableState<Int>,
    moviesTopRated: List<Movie>,
    totalMoviesUpcoming: MutableState<Int>,
    moviesUpcoming: List<Movie>,
    loadMoreRecommendation: () -> Unit,
    loadMorePopular: () -> Unit,
    loadMoreTopRated: () -> Unit,
    loadMoreUpcoming: () -> Unit,
    navToMovieDetail: (Int) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .padding(start = 10.dp, end = 10.dp)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "TMDB COMPOSE",
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 25.sp,
                color = colorCrimson,
                fontWeight = FontWeight.Bold,
            )
        }

        RecommendationView(
            totalMoviesRecommend = totalMoviesRecommend,
            movies = moviesRecommendation,
            navToMovieDetail = {
                navToMovieDetail.invoke(it)
            },
            loadMore = {
                loadMoreRecommendation.invoke()
            },
        )

        CategoryView(listGenre = genreList ?: emptyList())

        PopularView(
            totalMoviesPopular = totalMoviesPopular,
            movies = moviesPopular,
            navToMovieDetail = {
                navToMovieDetail.invoke(it)
            },
            loadMore = {
                loadMorePopular.invoke()
            },
        )

        TopRatedView(
            totalMoviesTopRated = totalMoviesTopRated,
            movies = moviesTopRated,
            navToMovieDetail = {
                navToMovieDetail.invoke(it)
            },
            loadMore = {
                loadMoreTopRated.invoke()
            },
        )

        UpcomingView(
            totalMoviesUpcoming = totalMoviesUpcoming,
            movies = moviesUpcoming,
            navToMovieDetail = {
                navToMovieDetail.invoke(it)
            },
            loadMore = {
                loadMoreUpcoming.invoke()
            },
        )
    }
}

@DevicePreviews
@Composable
fun PreviewHomeDetailScreen(
    @PreviewParameter(HomePreviewParameterProvider::class) homeFakeDataPreviewUI: HomeFakeDataPreviewUI,
) {
    val totalMoviesRecommend: MutableState<Int> = remember { mutableStateOf(100) }
    Box(
        modifier =
            Modifier
                .background(color = Color.White)
                .fillMaxSize(),
    ) {
        HomeScreenDetail(
            totalMoviesRecommend = totalMoviesRecommend,
            moviesRecommendation = homeFakeDataPreviewUI.listDbMovieFakeDataPreviewUI,
            genreList = homeFakeDataPreviewUI.listGenreFakeDataPreviewUI,
            totalMoviesPopular = totalMoviesRecommend,
            moviesPopular = homeFakeDataPreviewUI.listMovieFakeDataPreviewUI,
            totalMoviesTopRated = totalMoviesRecommend,
            moviesTopRated = homeFakeDataPreviewUI.listMovieFakeDataPreviewUI,
            totalMoviesUpcoming = totalMoviesRecommend,
            moviesUpcoming = homeFakeDataPreviewUI.listMovieFakeDataPreviewUI,
            loadMoreRecommendation = { /*TODO*/ },
            loadMorePopular = { /*TODO*/ },
            loadMoreTopRated = { /*TODO*/ },
            loadMoreUpcoming = { /*TODO*/ },
            navToMovieDetail = {},
        )
    }
}
