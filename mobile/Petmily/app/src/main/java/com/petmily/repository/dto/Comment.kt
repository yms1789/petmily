package com.petmily.repository.dto

data class Comment(
    var commentId: Long = 0L,
    var commentContent: String = "",
    var commentTime: String = "",
    var userEmail: String = "",
    var userNickname: String = "",
    var userProfileImg: String = "",
    var boardId: Long = 0L,
    var parentId: Long = 0L,
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
        commentId: Long = 0L,
        commentContent: String = "",
        commentTime: String = "",
        userEmail: String = "",
        userNickname: String = "",
        userProfileImg: String = "",
        boardId: Long = 0L,
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
