package com.tca.core.model.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
@Entity
data class DbMovies(
    @Id(assignable = true)
    var totalPages: Long = 0L,
    val totalResult: Int?,
) {
    override fun toString(): String = "DbMovies(totalPages=$totalPages, totalResult=$totalResult)"
}
