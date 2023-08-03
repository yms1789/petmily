package com.petmily.repository.dto

data class Chat(
    var chatId: Long,
) {
    constructor() : this (
        0L,
    )
}
