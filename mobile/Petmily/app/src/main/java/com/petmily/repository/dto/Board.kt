package com.petmily.repository.dto

import java.sql.Date

data class Board(
    var boardId: Long,
    var boardContent: String,
    var boardUploadTime: String,
    var userEmail: String,
) {
    constructor() : this(
        0L,
        "",
        "",
        "",
    )
    constructor(
        boardContent: String,
        userEmail: String
    ) : this(
        0L,
        boardContent,
        "",
        userEmail,
    )
}
