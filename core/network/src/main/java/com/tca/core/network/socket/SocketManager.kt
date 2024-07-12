package com.tca.core.network.socket

import com.tca.core.flow.StateModel
import com.tca.core.model.socket.SocketResponse
import com.tca.core.network.CoroutineDispatchers
import com.tca.core.network.socket.config.ErrorCodeDefine
import com.tca.core.network.socket.config.Socket
import com.tca.core.network.socket.config.SocketEvent
import com.tca.core.network.socket.config.SocketEventKey
import com.tca.core.network.socket.config.SocketOptions
import com.tca.core.network.socket.config.SocketState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SocketManager(
    private val json: Json,
    private val coroutineDispatchersIml: CoroutineDispatchers,
    private val socketUrl: String,
    private val okHttpClient: OkHttpClient
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = coroutineDispatchersIml.socket
    private var jobSocket: Job? = null
    val flowState: StateModel<SocketState> = StateModel(SocketState.INITIAL)
//    val flowUserStatus = SingleShotEventBus<List<UserStatus>?>()
//    val flowMessage = SingleShotEventBus<MessageEvent>()
//    val flowGroup = SingleShotEventBus<GroupEvent?>()
//    val flowEvent = BroadcastEventBus<Event?>()
//    val flowStartCall = SingleShotEventBus<CallEvent?>()
//    val flowCall = SingleShotEventBus<CallEvent?>()
//    val flowSecret = SingleShotEventBus<SecretEvent?>()
//    val flowGroupCall = SingleShotEventBus<GroupCallEvent?>()

    private val deviceID = "getAndroidID"

    private var socket: Socket? = null
    fun initSocket(socketToken: String?, callbackSuccess: suspend (isConnect: Boolean) -> Unit?) {
        val queryParams = mapOf("session" to (socketToken ?: ""), "&deviceid" to deviceID, "&version" to "1")
        socket = Socket(
            endpoint = "$socketUrl/data",
            config = SocketOptions(
                queryParams = queryParams,
                transport = SocketOptions.Transport.WEBSOCKET,
            ),
            logoutEvent = { flowState.updateEvent(SocketState.LOG_OUT) },
            okHttpClient = okHttpClient,
        ) {
            on(SocketEvent.Connect) {
                flowState.updateEvent(SocketState.CONNECTED)
                socket = this
            }

            on(SocketEvent.Connecting) {
                flowState.updateEvent(SocketState.CONNECTING)
            }

            on(SocketEvent.Disconnect) {
                updateStateDisconnect()
            }

            on(SocketEvent.Error) {
                onConnectError(it)
            }

            on(SocketEvent.Reconnect) {

            }

            on(SocketEvent.ReconnectAttempt) {
            }

            on(SocketEvent.Ping) {
            }

            on(SocketEvent.Pong) {
                // println("pong")
            }
            on(SocketEventKey.Incoming.SOCKET_LOGIN) {
                json.decodeFromString<SocketResponse>(it).let { data ->
                    if (data.result == 0 || data.uin != null) {
                        flowState.updateEvent(SocketState.LOGIN_SUCCESS)
                        launch {
                            callbackSuccess(true)
                        }
                    } else {
                        flowState.updateEvent(SocketState.DISCONNECT)
                    }
                }
            }
            listOf(
                SocketEventKey.Incoming.SOCKET_MESSAGE,
                SocketEventKey.Incoming.SOCKET_UPDATE_MESSAGE,
                SocketEventKey.Incoming.SOCKET_DRAFT_MESSAGE,
                SocketEventKey.Incoming.SOCKET_START_CALL,
                SocketEventKey.Incoming.SOCKET_STOP_CALL,
                SocketEventKey.Incoming.SOCKET_SDP_CALL,
                SocketEventKey.Incoming.SOCKET_EVENT,
                SocketEventKey.Incoming.SOCKET_RESULT,
                SocketEventKey.Incoming.SOCKET_DELETE_MESSAGE,
                SocketEventKey.Incoming.SOCKET_CHAT_MESSAGE,
                SocketEventKey.Incoming.SOCKET_START_SECRET_CHAT,
                SocketEventKey.Incoming.SOCKET_DELETE_SECRET_CHAT,
            ).forEach { eventName ->
                on(eventName) { data ->
                    launch {
                        try {
                            when (eventName) {
                                SocketEventKey.Incoming.SOCKET_MESSAGE,
                                SocketEventKey.Incoming.SOCKET_UPDATE_MESSAGE,
                                SocketEventKey.Incoming.SOCKET_DELETE_MESSAGE,
                                SocketEventKey.Incoming.SOCKET_DRAFT_MESSAGE,
                                -> {

                                }

                                SocketEventKey.Incoming.SOCKET_GROUP -> {

                                }

                                SocketEventKey.Incoming.SOCKET_CREATE_GROUP -> {

                                }

                                SocketEventKey.Incoming.SOCKET_DELETE_GROUP -> {

                                }

                                else -> {
                                    json.decodeFromString<SocketResponse>(data).let {
                                        when (eventName) {
                                            SocketEventKey.Incoming.SOCKET_EVENT -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_START_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_ANSWER_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_STOP_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_SDP_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_CANDIDATE_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_JOIN_GROUP_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_INVITE_GROUP_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_STOP_GROUP_CALL -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_UPDATE_GROUP -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_REACTION_EVENT -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_START_SECRET_CHAT -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_ACCEPT_SECRET_CHAT -> {

                                            }

                                            SocketEventKey.Incoming.SOCKET_DELETE_SECRET_CHAT -> {

                                            }

                                            else -> {}
                                        }
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        }
    }

    fun isConnecting(): Boolean {
        return flowState.value == SocketState.START || flowState.value == SocketState.CONNECTING || flowState.value == SocketState.CONNECTED || isConnect()
    }

    private fun isConnectStart(): Boolean {
        return flowState.value == SocketState.CONNECTING || flowState.value == SocketState.CONNECTED || isConnect()
    }


    fun isConnect(): Boolean {
        return try {
            socket?.isConnected() == true && flowState.value == SocketState.LOGIN_SUCCESS
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun connectSocket() {
        if (isConnectStart()) return
        socket?.connect()
    }

    private fun updateStateDisconnect() {
        if (flowState.value != SocketState.LOG_OUT && flowState.value != SocketState.INITIAL) {
            flowState.updateEvent(SocketState.DISCONNECT)
        }
    }

    fun updateStateStartSocket() {
        flowState.updateEvent(SocketState.START)
    }

    fun disConnectSocket(isLogout: Boolean = false) {
        launch {
            flowState.updateEvent(if (isLogout) SocketState.LOG_OUT else SocketState.INITIAL)
            jobSocket?.cancel()
            socket?.disconnect()
            socket = null
        }
    }

    fun checkConnectSocket(isNow: Boolean = false) {
        if (isConnecting()) return
        if (isNow) {
            jobSocket?.cancel()
            jobSocket = SupervisorJob()
            jobSocket = launch {
                connectSocket()
                jobSocket?.cancel()
            }
            return
        }
        launch {
            retrySuspend(
                initialDelay = 3.seconds,
                shouldRetry = { flowState.value == SocketState.DISCONNECT },
            ) {
                connectSocket()
            }
        }
    }

    private fun onConnectError(errorCode: Int?) {
        updateStateDisconnect()
        when (errorCode) {
            ErrorCodeDefine.ERRORCODE_IOException_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_OK_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_FAILED_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_EXPIRED_VALUE -> {
                flowState.updateEvent(SocketState.LOG_OUT)
            }

            ErrorCodeDefine.ERRORCODE_NONEXISTENT_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_INVALID_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_WRONG_TYPE_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_PARSING_DATA_FAILURE_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_LOGIN_CONFLICT_VALUE -> {
                flowState.updateEvent(SocketState.LOG_OUT)
            }

            ErrorCodeDefine.ERRORCODE_USER_NOT_FOUND_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_HEADER_DECODING_FAILURE_VALUE -> {
            }

            ErrorCodeDefine.ERRORCODE_WRONG_SESSION_VALUE -> {
            }
        }
    }
}

suspend inline fun <T> retrySuspend(
    initialDelay: Duration,
    shouldRetry: () -> Boolean = { true },
    block: (times: Int) -> T,
) {
    var times = Int.MAX_VALUE
    while (shouldRetry() && times > 0) {
        block(times)
        times--
        delay(initialDelay)
    }
}
