package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class Movies(
    @SerialName(value = "page") val page: Int,
    @SerialName(value = "total_results") val total_results: Int,
    @SerialName(value = "total_pages") val total_pages: Int,
    @SerialName(value = "results") val results: List<Movie> = emptyList(),
) {
    override fun toString(): String = "Movies(page=$page, total_results=$total_results, total_pages=$total_pages, results=$results)"
}
