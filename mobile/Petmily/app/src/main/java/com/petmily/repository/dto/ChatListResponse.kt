package com.petmily.repository.dto

data class ChatListResponse(
    var roomId: String = "",
    var participants: MutableList<UserLoginInfoDto> = mutableListOf(),
    var latestMessage: String = "",
    var unreadMessageCount: Long = 0L,
)
