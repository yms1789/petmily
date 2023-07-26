package com.petmily.repository.dto

import java.sql.Date

data class User(
    var id: Long,
    var email: String,
    var password: String,
    var token: String,
    var nickname: String,
    var region: String,
    var profile_image: String,
    var like_pet: String,
    var point: Int,
    var badge: Long,
    var ring: Long,
    var background: Long,
    var login_date: Date,
    var is_social: Boolean,
) {
    constructor() :
        this(
            0L,
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0L,
            0L,
            0L,
            Date(System.currentTimeMillis()),
            false,
        )

    constructor(email: String, password: String) :
        this(
            0L,
            email,
            password,
            "",
            "",
            "",
            "",
            "",
            0,
            0L,
            0L,
            0L,
            Date(System.currentTimeMillis()),
            false,
        )
}
