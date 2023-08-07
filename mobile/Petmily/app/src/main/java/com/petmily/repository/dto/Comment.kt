package com.petmily.repository.dto

data class Comment(
    var commentId: Long,
    var commentContent: String,
    var commentTime: String,
    var userEmail: String,
    var boardId: Long,
    var parentId: Long,
) {
    constructor() : this(
        0L,
        "",
        "",
        "",
        0L,
        0L,
    )
    constructor(
        commentId: Long,
        commentContent: String,
        commentTime: String,
        userEmail: String,
        boardId: Long,
    ) : this(
        commentId,
        commentContent,
        commentTime,
        userEmail,
        boardId,
        0L,
    )
}
