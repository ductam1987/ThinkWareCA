package com.tca.feature.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.tca.core.model.db.DbMovie
import com.tca.core.ui.DevicePreviews
// import com.tmdb.core.model.fakeData.listDbMovieFakeData
import com.tca.core.ui.LoadImage
import com.tca.core.ui.OnLastItemReached
import com.tca.core.ui.TitleView
import com.tca.core.ui.convertImageURL
import com.tca.core.ui.dataTest.HomeFakeDataPreviewUI
import com.tca.core.ui.dataTest.HomePreviewParameterProvider
import com.tca.feature.home.R

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Composable
fun RecommendationView(
    totalMoviesRecommend: MutableState<Int>,
    movies: List<DbMovie>,
    loadMore: () -> Unit,
    navToMovieDetail: (Int) -> Unit?,
) {
    Column(modifier = Modifier.padding(top = 10.dp, start = 10.dp, end = 10.dp)) {
        TitleView(
            stringResourceId = R.string.recommendation,
            imgResourceId = R.drawable.ic_arrow_right,
        )

        val listState = rememberLazyListState()
        LazyRow(
            modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
            state = listState,
        ) {
            items(
                count = movies.distinct().size,
                key = { index ->
                    movies.distinct()[index].idMovie
                },
            ) { index ->
                RecommendationViewDetail(movies.distinct()[index], navToMovieDetail)
            }
        }

        listState.OnLastItemReached {
            if (movies.isNotEmpty() && movies.distinct().size < totalMoviesRecommend.value) {
                loadMore.invoke()
            }
        }
    }
}

@Composable
fun RecommendationViewDetail(
    movie: DbMovie?,
    navToMovieDetail: (Int) -> Unit?,
) {
    Box(
        modifier =
            Modifier
                .size(width = 300.dp, height = 150.dp)
                .padding(end = 10.dp),
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        ) {
            LoadImage(
                imageUrl = movie?.backdropPath?.convertImageURL(),
                imageWith = 300,
                imageHeight = 150,
                onClick = {
                    navToMovieDetail.invoke(movie?.idMovie?.toInt() ?: 0)
                },
            )
        }
    }
}

@DevicePreviews
@Composable
fun PreviewRecommendation(
    @PreviewParameter(HomePreviewParameterProvider::class) homeFakeDataPreviewUI: HomeFakeDataPreviewUI,
) {
    val moviesRecommendation = remember { mutableStateListOf<DbMovie>() }
    val totalMoviesRecommend: MutableState<Int> = remember { mutableStateOf(0) }
    moviesRecommendation.addAll(homeFakeDataPreviewUI.listDbMovieFakeDataPreviewUI)
    totalMoviesRecommend.value = 100
    RecommendationView(
        totalMoviesRecommend,
        moviesRecommendation,
        navToMovieDetail = {},
        loadMore = {},
    )
}
