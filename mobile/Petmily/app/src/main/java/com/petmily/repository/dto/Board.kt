package com.petmily.repository.dto

data class Board(
    var boardId: Long = 0L,
    var boardContent: String = "",
    var boardUploadTime: String = "",
    var heartCount: Int = 0,
    var userEmail: String = "",
    var userProfileImageUrl: String = "",
    var userNickname: String = "",
    var photoUrls: List<String> = listOf(),
    var hashTags: List<String> = listOf(),
    var comments: List<Comment> = listOf(),
    var likedByCurrentUser: Boolean = false,
) {
    constructor() : this(
        0L,
        "",
        "",
        0,
        "",
        "",
        "",
        listOf(),
        listOf(),
        listOf(),
        false,
    )
    constructor(
        boardContent: String,
        userEmail: String,
    ) : this(
        0L,
        boardContent,
        "",
        0,
        userEmail,
        "",
        "",
        listOf(),
        listOf(),
        listOf(),
        false,
    )
}
