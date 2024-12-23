package com.tca.feature.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tca.core.model.network.Movie
import com.tca.core.ui.MovieVerticalView
import com.tca.core.ui.OnLastItemReached
import com.tca.core.ui.TitleView
import com.tca.feature.home.R

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Composable
fun PopularView(
    totalMoviesPopular: MutableState<Int>,
    movies: List<Movie>,
    navToMovieDetail: (Int) -> Unit?,
    loadMore: () -> Unit,
) {
    Column(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
        TitleView(stringResourceId = R.string.popular, imgResourceId = R.drawable.ic_arrow_right)

        val listState = rememberLazyListState()
        LazyRow(
            state = listState,
            modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
        ) {
            items(
                count = movies.distinct().size,
                key = { index ->
                    movies.distinct()[index].id
                },
            ) { index ->
                PopularViewDetail(movies.distinct()[index], navToMovieDetail)
            }
        }

        listState.OnLastItemReached {
            if (movies.isNotEmpty() && movies.distinct().size < totalMoviesPopular.value) {
                loadMore.invoke()
            }
        }
    }
}

@Composable
fun PopularViewDetail(
    movie: Movie,
    navToMovieDetail: (Int) -> Unit?,
) {
    MovieVerticalView(
        movieImg = movie.poster_path,
        movieName = movie.title,
        movieId = movie.id,
        navToMovieDetail = navToMovieDetail,
    )
}
