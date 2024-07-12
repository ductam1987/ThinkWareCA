package com.tca.core.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Tam Nguyen on 11/07/2024.
 */

@Serializable
data class AuthorDetail(
    @SerialName(value = "name") val name: String?,
    @SerialName(value = "username") val username: String?,
    @SerialName(value = "avatar_path") val avatar_path: String?,
) {
    override fun toString(): String = "AuthorDetail(name=$name, username=$username, avatar_path=$avatar_path)"
}
