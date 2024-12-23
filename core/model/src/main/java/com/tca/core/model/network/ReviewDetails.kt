package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class ReviewDetails(
    @SerialName(value = "author") val author: String,
    @SerialName(value = "content") val content: String,
    @SerialName(value = "author_details") val authorDetail: AuthorDetail,
    @SerialName(value = "id") val id: String,
    @SerialName(value = "url") val url: String,
    @SerialName(value = "updated_at") val updatedAt: String,
) {
    override fun toString(): String =
        "ReviewDetails(author='$author', content='$content', authorDetail=$authorDetail, id='$id', url='$url', updatedAt='$updatedAt')"
}
