package com.tca.core.network.socket.config

sealed interface SocketResource<T> {
    data class Success<T>(val data: T? = null) : SocketResource<T>
    data class Failed<T>(val throwable: Throwable? = null, val exception: Exception? = null, val message: String? = null) : SocketResource<T>
}
