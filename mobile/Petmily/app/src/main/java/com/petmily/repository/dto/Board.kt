package com.petmily.repository.dto

data class Board(
    var boardId: Long,
    var boardContent: String,
    var boardUploadTime: String,
    var userEmail: String,
    var photoUrls: List<String>,
) {
    constructor() : this(
        0L,
        "",
        "",
        "",
        listOf(),
    )
    constructor(
        boardContent: String,
        userEmail: String,
    ) : this(
        0L,
        boardContent,
        "",
        userEmail,
        listOf(),
    )
}
