package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class Credits(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "cast") val cast: ArrayList<Cast>?,
    @SerialName(value = "crew") val crew: ArrayList<Crew>?,
) {
    override fun toString(): String = "Credits(id=$id, cast=$cast, crew=$crew)"
}
