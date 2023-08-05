package com.petmily.repository.dto

data class CurationBookmark (
    var userEmail: String,
    var cid: Long
) {
    constructor(): this(
        "",
        0
    )
}