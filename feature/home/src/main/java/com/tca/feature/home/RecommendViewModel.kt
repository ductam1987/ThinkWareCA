package com.tca.feature.home

import Constants
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tca.core.model.db.SubDbMovie
import com.tca.core.model.mapper.MoviesMapper
import com.tca.core.model.mapper.RecommendationMapper
import com.tca.core.model.network.Categories
import com.tca.core.model.network.Movies
import com.tca.core.network.CoroutineDispatchers
import com.tca.core.network.config.RecommendConfigNetwork
import com.tca.core.repos.LocalDbRepository
import com.tca.core.repos.TCARepository
import com.tca.core.flow.StateModel
import com.tca.core.repos.SocketRepository
import com.tca.core.ui.module.RecommendationModule
import com.tca.core.ui.network.ResultData
import kotlinx.coroutines.launch

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class RecommendViewModel(
    private val socketRepositoryImpl: SocketRepository,
    private val coroutineDispatchersIml: CoroutineDispatchers,
    private val tmDBRepositoryImpl: TCARepository,
    private val coroutineDispatchersImpl: CoroutineDispatchers,
    private val localDbRepositoryImpl: LocalDbRepository,
    private val recommendationMapper: RecommendationMapper,
    private val moviesMapper: MoviesMapper,
) : ViewModel() {
    val genre = StateModel<Categories?>(null)

    fun getTMDBRecommend(
        paging: Int,
        callbackMovie: (SubDbMovie?) -> Unit,
    ) {
        val recommendConfig =
            RecommendConfigNetwork(
                movieId = Constants.MOVIE_ID,
                apiKey = Constants.API_KEY,
                page = paging,
            )
        viewModelScope.launch {
            RecommendationModule
                .provideRecommendationModule(
                    recommendationMapper = recommendationMapper,
                    moviesMapper = moviesMapper,
                    coroutineDispatchers = coroutineDispatchersImpl,
                    localDbRepository = localDbRepositoryImpl,
                    tmdbRepository = tmDBRepositoryImpl,
                    recommendConfig = recommendConfig,
                ).collect { state ->
                    when (state) {
                        is ResultData.SuccessLocal -> {
                            callbackMovie.invoke(state.data)
                        }

                        is ResultData.SuccessMerged -> {
                            callbackMovie.invoke(state.data)
                        }

                        is ResultData.Error -> {
                        }

                        is ResultData.Loading -> {
                        }
                    }
                }
        }
    }

    fun getTMDBGender() {
        viewModelScope.launch(coroutineDispatchersImpl.io) {
            genre.updateEvent(tmDBRepositoryImpl.getGender(Constants.API_KEY))
        }
    }

    fun getTMDBPopular(
        paging: Int,
        callbackMovie: (Movies?) -> Unit,
    ) {
        viewModelScope.launch {
            tmDBRepositoryImpl.getMoviesPopular(apiKey = Constants.API_KEY, page = paging).let {
                callbackMovie(it)
            }
        }
    }

    fun getTMDBTopRated(
        paging: Int,
        callbackMovie: (Movies?) -> Unit,
    ) {
        viewModelScope.launch {
            tmDBRepositoryImpl.getMoviesTopRated(apiKey = Constants.API_KEY, page = paging).let {
                callbackMovie(it)
            }
        }
    }

    fun getTMDBUpcoming(
        paging: Int,
        callbackMovie: (Movies?) -> Unit,
    ) {
        viewModelScope.launch {
            tmDBRepositoryImpl.getMoviesUpcoming(apiKey = Constants.API_KEY, page = paging).let {
                callbackMovie(it)
            }
        }
    }

    /**
     * In the real project, you can use this function in the Base ViewModel or the first screen
     */
    fun initSocket() {
        viewModelScope.launch(coroutineDispatchersIml.socket) {
            socketRepositoryImpl.connectSocket(
                socketToken = "Socket Token",
                callbackSuccess = {
                    /**
                     * Check connect socket success & do something
                     */
                })
        }

        viewModelScope.launch { listenerState() }
    }

    private suspend fun listenerState() {
        socketRepositoryImpl.socketManager.flowState.collectLatest { state ->
            /**
             * Listen socket state
             */
        }
    }
}
