package com.petmily.repository.dto

data class Comment(
    var commentId: Long,
    var commentContent: String,
    var commentTime: String,
    var userEmail: String,
    var userNickname: String,
    var userProfileImg: String,
    var boardId: Long,
    var parentId: Long,
) {
    constructor() : this(
        0L,
        "",
        "",
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
        userNickname: String,
        userProfileImg: String,
        boardId: Long,
    ) : this(
        commentId,
        commentContent,
        commentTime,
        userEmail,
        userNickname,
        userProfileImg,
        boardId,
        0L,
    )
}
