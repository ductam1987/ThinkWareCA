package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class Reviews(
    @SerialName(value = "id") val id: Int,
    @SerialName(value = "page") val page: Int,
    @SerialName(value = "results") val results: ArrayList<ReviewDetails>?,
    @SerialName(value = "total_pages") val total_pages: Int,
    @SerialName(value = "total_results") val total_results: Int,
) {
    override fun toString(): String = "Reviews(id=$id, page=$page, results=$results, total_pages=$total_pages, total_result=$total_results)"
}
