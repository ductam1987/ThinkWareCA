package com.tca.core.model.socket

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocketListResponse(
    @SerialName("result")
    val result: Int = 0, // 0
    @SerialName("pindex")
    val pindex: Int? = null, // 0
    @SerialName("psize")
    val psize: Int? = null, // 50
)
