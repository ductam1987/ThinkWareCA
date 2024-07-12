package com.tca.core.network.socket.config

data class SocketOptions(
    val queryParams: Map<String, String>?,
    val transport: Transport = Transport.DEFAULT,
) {
    sealed class Transport {
        object WEBSOCKET : Transport()
        object POLLING : Transport()
        object DEFAULT : Transport()
    }
}
