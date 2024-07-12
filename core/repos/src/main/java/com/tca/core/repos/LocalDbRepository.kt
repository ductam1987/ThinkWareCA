package com.tca.core.repos

import android.content.Context
import com.tca.core.model.db.DbMovie
import com.tca.core.model.db.DbMovieDetail
import com.tca.core.model.db.DbMovies
import com.tca.core.network.config.RecommendConfigDb
import io.objectbox.BoxStore

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
interface LocalDbRepository {
    fun init(context: Context)

    fun getBox(): BoxStore

    fun getListMovie(recommendConfigDb: RecommendConfigDb): List<DbMovie>

    fun getMovies(): DbMovies?

    fun getMovieDetail(movieId: Long): DbMovieDetail?

    suspend fun saveListMovie(listDbMovie: List<DbMovie>)

    suspend fun saveMovies(dbMovies: DbMovies)

    suspend fun saveMovieDetail(dbMovieDetail: DbMovieDetail)
}
