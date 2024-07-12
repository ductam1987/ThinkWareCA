package com.tca.core.network.socket.config

import io.socket.client.Ack
import io.socket.client.IO
import io.socket.engineio.client.transports.Polling
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume

class Socket(
    endpoint: String,
    okHttpClient: OkHttpClient,
    config: SocketOptions?,
    private val logoutEvent: () -> Unit,
    build: SocketBuilder.() -> Unit,
) {
    private var socketIo: io.socket.client.Socket = IO.socket(
        endpoint,
        IO.Options().apply {
            reconnection = false
            callFactory = okHttpClient
            webSocketFactory = okHttpClient
            transports = config?.transport?.let {
                when (it) {
                    SocketOptions.Transport.DEFAULT -> return@let null
                    SocketOptions.Transport.WEBSOCKET -> return@let arrayOf(WebSocket.NAME)
                    SocketOptions.Transport.POLLING -> return@let arrayOf(Polling.NAME)
                }
            }
            query = config?.queryParams?.run {
                if (size.isNull) return@run null
                var result = ""
                forEach { (key, value) ->
                    result += "$key=$value"
                }
                result
            }
        },
    )

    init {

        object : SocketBuilder {
            override fun on(event: String, action: Socket.(message: String) -> Unit) {
                socketIo.on(event) {
                    this@Socket.action(rawDataToString(it))
                }
            }

            override fun <T> on(socketEvent: SocketEvent<T>, action: Socket.(data: T) -> Unit) {
                socketEvent.socketIoEvents.forEach { event ->
                    socketIo.on(event) { data ->
                        this@Socket.action(socketEvent.mapper(data))
                    }
                }
            }
        }.build()
    }

    fun emit(event: String, data: JsonObject) {
        socketIo.emit(event, JSONObject(data.toString()))
    }

    fun emit(event: String, data: JsonArray) {
        socketIo.emit(event, JSONArray(data.toString()))
    }

    fun emit(event: String, data: String) {
        socketIo.emit(
            event,
            JSONObject(data),
            Ack { args: Array<Any> ->
            },
        )
    }

    suspend fun emitAwait(event: String, request: String, isCallBack: Boolean = false): SocketResource<JSONObject> =
        suspendCancellableCoroutine { cont ->
            if (isConnected()) {
                socketIo.emit(
                    event,
                    JSONObject(request),
                    Ack { args: Array<Any> ->
                        try {
                            val jsonObject = args.firstOrNull() as JSONObject
                            if (resultSuccess(jsonObject)) {
                                cont.resume(SocketResource.Success(data = jsonObject))
                            } else if (resultExpired(jsonObject)) {
                                logoutEvent()
                            } else {
                                cont.resume(SocketResource.Failed(message = jsonObject.toString()))
                            }
                        } catch (e: Exception) {
                            cont.resume(SocketResource.Failed(exception = e))
                        }
                    },
                )
                if (isCallBack) cont.resume(SocketResource.Success(null))
            } else {
                cont.resume(SocketResource.Failed())
            }
        }

    private fun resultSuccess(jsonObject: JSONObject) = jsonObject.has(SocketEventKey.Incoming.SOCKET_RESULT) && jsonObject.optInt(SocketEventKey.Incoming.SOCKET_RESULT) == 0

    private fun resultExpired(jsonObject: JSONObject) = jsonObject.has(SocketEventKey.Incoming.SOCKET_RESULT) && jsonObject.optInt(SocketEventKey.Incoming.SOCKET_RESULT) == 2

    fun connect() {
        socketIo.connect()
    }

    fun disconnect() {
        socketIo.disconnect()
    }

    fun isConnected(): Boolean {
        return socketIo.connected()
    }

    private companion object {
        fun rawDataToString(data: Array<out Any>): String {
            return data.last().toString()
        }
    }

    val Any?.isNull
        get() = when (this) {
            (this == null) -> true
            is Collection<*>? -> (this?.size ?: 0) == 0 || this?.isEmpty() == true
            is String? -> this?.length == 0 || isNullOrBlank()
            is Long? -> this == 0L
            is Double? -> this == 0.0
            is Int? -> this == 0
            else -> this == null
        }
}
