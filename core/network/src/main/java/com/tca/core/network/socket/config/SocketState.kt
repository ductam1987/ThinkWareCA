package com.tca.core.network.socket.config

sealed class SocketState {
    object INITIAL : SocketState()
    object START : SocketState()
    object CONNECTED : SocketState()
    object CONNECTING : SocketState()
    object DISCONNECT : SocketState()
    object LOGIN_SUCCESS : SocketState()
    object LOG_OUT : SocketState()
}
