package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class Genre(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "name") val name: String? = null,
) {
    override fun toString(): String = "Genre(id=$id, name='$name')"
}
