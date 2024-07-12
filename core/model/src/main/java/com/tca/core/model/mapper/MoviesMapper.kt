package com.tca.core.model.mapper

import com.tca.core.model.db.DbMovies
import com.tca.core.model.network.Movies

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class MoviesMapper : EntityMapper<DbMovies, Movies> {
    override fun mapToEntity(domain: Movies): DbMovies =
        DbMovies(
            totalPages = domain.total_pages.toLong(),
            totalResult = domain.total_results,
        )

    override fun mapFromEntity(entity: DbMovies): Movies {
        TODO("Not yet implemented")
    }
}
