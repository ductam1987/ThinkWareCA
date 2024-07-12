package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class Videos(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "results") val results: ArrayList<VideoDetails>?,
) {
    override fun toString(): String = "Videos(id=$id, results=$results)"
}
