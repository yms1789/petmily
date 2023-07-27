package com.petmily.repository.dto

import java.sql.Date

data class Board(
    var boardId: Long,
    var boardContent: String,
    var boardUploadTime: Date,
    var user: User,
) {
    constructor() : this(
        0L,
        "",
        Date(System.currentTimeMillis()),
        User(),
    )
}
