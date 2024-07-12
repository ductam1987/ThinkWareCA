package com.tca.core.repos

import com.tca.core.network.socket.SocketManager
import com.tca.core.network.socket.config.SocketState

class SocketRepositoryImpl(socketManagerIml: SocketManager) : SocketRepository {

    override val socketManager: SocketManager = socketManagerIml

    override val flowState: SocketState = socketManagerIml.flowState.value

    override fun isConnecting() = socketManager.isConnecting()

    override fun isConnectSocket() = socketManager.isConnect()

    override fun disConnectSocket() = socketManager.disConnectSocket()

    override fun updateStateStartSocket() = socketManager.updateStateStartSocket()

    override suspend fun connectSocket(socketToken: String?, callbackSuccess: suspend (isConnect: Boolean) -> Unit?) {
        socketManager.initSocket(socketToken = socketToken, callbackSuccess = callbackSuccess)
        socketManager.connectSocket()
    }

    override fun checkConnectSocket(isNow: Boolean) = socketManager.checkConnectSocket(isNow = isNow)

    override suspend fun clearSocket(isLogout: Boolean) {
        socketManager.disConnectSocket(isLogout = isLogout)
    }

//    override suspend fun sendMessage(createMessageRequest: CreateMessageRequest): Resource<Message> {
//        return socketManager.createMessage(createMessageRequest)
//    }
//
//    override suspend fun updateStatusMessage(messageRequest: MessageRequest): Resource<Boolean> {
//        return socketManager.updateStatusMessage(messageRequest)
//    }
//
//    override suspend fun deleteMessage(deleteMessageRequest: DeleteMessageRequest): Resource<Boolean> {
//        return socketManager.deleteMessage(deleteMessageRequest)
//    }
//
//    override suspend fun checkGroup(checkGroupRequest: CheckGroupRequest): Resource<Group> {
//        return socketManager.checkGroup(checkGroupRequest)
//    }
//
//    override suspend fun pinMessage(pinMessageRequest: PinMessageRequest): Resource<Boolean> {
//        return socketManager.pinMessage(pinMessageRequest)
//    }
//
//    override suspend fun unPinMessage(unPinMessageRequest: UnPinMessageRequest): Resource<Boolean> {
//        return socketManager.unPinMessage(unPinMessageRequest)
//    }
//
//    override suspend fun unPinAllMessage(unPinAllMessageRequest: UnPinAllMessageRequest): Resource<Boolean> {
//        return socketManager.unPinAllMessage(unPinAllMessageRequest)
//    }
//
//    override suspend fun listPinnedMessage(listPinMessageRequest: ListPinMessageRequest): Resource<List<Message>> {
//        return socketManager.listPinnedMessage(listPinMessageRequest)
//    }
//
//    override suspend fun checkCall(checkCallRequest: CheckCallRequest): Resource<Int> {
//        return socketManager.checkCall(checkCallRequest)
//    }
//
//    override suspend fun createGroupCall(createGroupCallRequest: CreateGroupCallRequest): Resource<CreateGroupCall> {
//        return socketManager.createGroupCall(createGroupCallRequest)
//    }
//
//    override suspend fun joinGroupCall(joinGroupCallRequest: JoinGroupCallRequest): Resource<SocketResponse> {
//        return socketManager.joinGroupCall(joinGroupCallRequest)
//    }
//
//    override suspend fun leaveGroupCall(leaveGroupCallRequest: LeaveGroupCallRequest): Resource<Boolean> {
//        return socketManager.leaveGroupCall(leaveGroupCallRequest)
//    }
//
//    override suspend fun sendEvent(eventRequest: EventRequest) {
//        socketManager.sendEvent(eventRequest)
//    }
//
//    override suspend fun createGroup(createGroupRequest: CreateGroupRequest): Resource<Group> {
//        return socketManager.createGroup(createGroupRequest)
//    }
}
