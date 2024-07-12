package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class ProductCountries(
    @SerialName(value = "iso_3166_1") val iso_3166_1: String?,
    @SerialName(value = "name") val name: String?,
) {
    override fun toString(): String = "ProductCountries(iso_3166_1=$iso_3166_1, name=$name)"
}
