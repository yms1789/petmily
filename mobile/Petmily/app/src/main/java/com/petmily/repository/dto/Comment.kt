package com.petmily.repository.dto

data class Comment(
    val commentId: Long,
) {
    constructor() : this(
        0L,
    )
}
