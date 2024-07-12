package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class SpokenLanguages(
    @SerialName(value = "iso_639_1") val iso_639_1: String?,
    @SerialName(value = "name") val name: String?,
) {
    override fun toString(): String = "SpokenLanguages(iso_639_1=$iso_639_1, name=$name)"
}
