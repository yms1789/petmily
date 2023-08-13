package com.petmily.repository.dto

data class Chat(
    var chatId: Long = 0L,
    var chatUserImage: String = "",
    var username: String = "",
    var text: String = "",
    var time: String = "",
    val isSelf: Boolean = false
)
