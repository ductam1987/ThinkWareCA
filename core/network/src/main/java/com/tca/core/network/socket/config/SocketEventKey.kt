package com.tca.core.network.socket.config


sealed interface SocketEventKey {

    data object Incoming : SocketEventKey {
        const val SOCKET_LOGIN = "login"
        const val SOCKET_USER_STATUS = "user_status"
        const val SOCKET_MESSAGE = "message"
        const val SOCKET_UPDATE_MESSAGE = "update_message"
        const val SOCKET_GROUP = "group"
        const val SOCKET_UPDATE_GROUP = "update_group"
        const val SOCKET_CREATE_GROUP = "create_group"
        const val SOCKET_START_CALL = "start_call"
        const val SOCKET_ANSWER_CALL = "answer_call"
        const val SOCKET_STOP_CALL = "stop_call"
        const val SOCKET_SDP_CALL = "ice_sdp"
        const val SOCKET_CANDIDATE_CALL = "ice_candidate"
        const val SOCKET_EVENT = "event"
        const val SOCKET_RESULT = "result"
        const val SOCKET_DELETE_MESSAGE = "delete_message"
        const val SOCKET_CHAT_MESSAGE = "chat_message"
        const val SOCKET_START_SECRET_CHAT = "start_secret_chat"
        const val SOCKET_ACCEPT_SECRET_CHAT = "accept_secret_chat"
        const val SOCKET_DELETE_SECRET_CHAT = "delete_secret_chat"
        const val SOCKET_CREATE_GROUP_CALL = "create_groupcall"
        const val SOCKET_JOIN_GROUP_CALL = "join_groupcall"
        const val SOCKET_LEAVE_GROUP_CALL = "leave_groupcall"
        const val SOCKET_INVITE_GROUP_CALL = "invite_groupcall"
        const val SOCKET_STOP_GROUP_CALL = "stop_groupcall"
        const val SOCKET_DELETE_GROUP = "delete_group"
        const val SOCKET_LIVE_LOCATION = "live_location"
        const val SOCKET_NEW_REGISTER_GROUP = "new_register_group"
        const val SOCKET_REACTION_EVENT = "reaction_message"
        const val SOCKET_DRAFT_MESSAGE = "draft_message"
    }
}
