package com.tca.core.model.mapper

import com.tca.core.model.db.DbMovie
import com.tca.core.model.network.Movie

/**
 * Created by Tam Nguyen on 11/07/2024.
 */
class RecommendationMapper : EntityMapper<DbMovie, Movie> {
    override fun mapFromEntity(entity: DbMovie): Movie {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domain: Movie): DbMovie =
        DbMovie(
            idMovie = domain.id.toLong(),
            posterPath = domain.poster_path,
            popularity = domain.popularity,
            voteCount = domain.vote_count,
            video = domain.video,
            mediaType = domain.media_type,
            adult = domain.adult,
            backdropPath = domain.backdrop_path,
            originalLanguage = domain.original_language,
            originalTitle = domain.original_title,
            genreIds = domain.genre_ids,
            title = domain.title,
            voteAverage = domain.vote_average,
            overview = domain.overview,
            releaseDate = domain.release_date,
        )
}
