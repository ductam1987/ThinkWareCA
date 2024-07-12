package com.tca.core.network.socket.config

interface SocketBuilder {
    fun on(event: String, action: Socket.(message: String) -> Unit)
    fun <T> on(socketEvent: SocketEvent<T>, action: Socket.(array: T) -> Unit)

    fun on(vararg events: String, action: Socket.(message: String) -> Unit) {
        events.forEach {
            on(it, action)
        }
    }

    fun <T> on(vararg socketEvents: SocketEvent<T>, action: Socket.(array: T) -> Unit) {
        socketEvents.forEach {
            on(it, action)
        }
    }
}
