package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class Categories(
    @SerialName(value = "genres") val genres: List<Genre>?,
) {
    override fun toString(): String = "Categories(genres=$genres)"
}
