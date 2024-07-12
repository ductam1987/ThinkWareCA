package com.tca.core.network.socket.config


import io.socket.client.Socket
import io.socket.engineio.client.EngineIOException
import org.json.JSONObject

sealed class SocketEvent<T> : Mapper<T> {
    object Connect : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_CONNECT)
    }

    object Connecting : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_CONNECTING)
    }

    object Disconnect : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_DISCONNECT)
    }

    data object Error : SocketEvent<Int?>() {
        override val socketIoEvents: List<String> = listOf(
            Socket.EVENT_ERROR,
            Socket.EVENT_CONNECT_ERROR,
            Socket.EVENT_CONNECT_TIMEOUT,
            Socket.EVENT_RECONNECT_ERROR,
        )

        override fun mapper(array: Array<out Any>): Int? {
            return if (array[0] is EngineIOException) {
                ErrorCodeDefine.ERRORCODE_FAILED_VALUE
            } else if (array.size == 1) {
                (array.first() as? JSONObject)?.getInt("error")
            } else {
                ErrorCodeDefine.ERRORCODE_IOException_VALUE
            }
        }
    }

    object Message : SocketEvent<Any>() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_MESSAGE)
        override fun mapper(array: Array<out Any>): Any {
            return array
        }
    }

    object Reconnect : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_RECONNECT)
    }

    object ReconnectAttempt : SocketEvent<Int>() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_RECONNECT_ATTEMPT)
        override fun mapper(array: Array<out Any>): Int {
            return array[0] as Int
        }
    }

    object Ping : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_PING)
    }

    object Pong : SocketEvent<Unit>(), Mapper<Unit> by UnitMapper() {
        override val socketIoEvents: List<String> = listOf(Socket.EVENT_PONG)
    }

    abstract val socketIoEvents: List<String>

    private class UnitMapper : Mapper<Unit> {
        override fun mapper(array: Array<out Any>) = Unit
    }
}
