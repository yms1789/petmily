package com.petmily.repository.dto

import java.sql.Date

data class Curation(
    var curationId: Long,
    var curationTitle: String,
    var curationContent: String,
    var curationImage: String,
    var curationUrl: String,
    var curationDate: Date,
) {
    constructor(): this(
        0L,
        "",
        "",
        "",
        "",
        Date(System.currentTimeMillis()),
    )
}
