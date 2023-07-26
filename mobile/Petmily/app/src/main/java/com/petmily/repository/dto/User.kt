package com.petmily.repository.dto

import java.sql.Date

// TODO: 차후 수정 필요
data class User(
    val id: Long,
    val email: String,
    val password: String,
    val token: String,
    val nickname: String,
    val region: String,
    val profile_image: String,
    val like_pet: String,
    val point: Int,
    val badge: Long,
    val ring: Long,
    val background: Long,
    val login_date: Date,
    val is_social: Boolean,
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
