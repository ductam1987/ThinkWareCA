package com.tca.core.repos

import com.tca.core.network.socket.SocketManager
import com.tca.core.network.socket.config.SocketState

interface SocketRepository {

    val socketManager: SocketManager

    val flowState: SocketState

    fun isConnecting(): Boolean

    fun updateStateStartSocket()

    fun isConnectSocket(): Boolean

    fun disConnectSocket()

    suspend fun connectSocket(socketToken: String?, callbackSuccess: suspend (isConnect: Boolean) -> Unit?)

    fun checkConnectSocket(isNow: Boolean = false)

    suspend fun clearSocket(isLogout: Boolean = false)

    /**
     * SocketIO send data
     */
//    suspend fun sendMessage(createMessageRequest: CreateMessageRequest): SocketResource<Message>
//
//    suspend fun deleteMessage(deleteMessageRequest: DeleteMessageRequest): SocketResource<Boolean>
//
//    suspend fun sendEvent(eventRequest: EventRequest)

}
