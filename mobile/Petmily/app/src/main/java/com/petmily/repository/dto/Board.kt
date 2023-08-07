package com.petmily.repository.dto

data class Board(
    var boardId: Long,
    var boardContent: String,
    var boardUploadTime: String,
    var heartCount: Int,
    var userEmail: String,
    var userProfileImageUrl: String,
    var userNickname: String,
    var photoUrls: List<String>,
    var hashTags: List<String>,
    var comments: List<Comment>,
    var likedByCurrentUser: Boolean,
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
