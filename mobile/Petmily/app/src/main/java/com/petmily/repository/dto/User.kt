package com.petmily.repository.dto

import java.sql.Date

data class User(
    var userId: Long,
    var userEmail: String,
    var userPw: String,
    var userToken: String,
    var userNickname: String,
    var userRegion: String,
    var userProfileImg: String,
    var userLikePet: String,
    var userPoint: Int,
    var userBadge: Long,
    var userRing: Long,
    var userBackground: Long,
    var userLoginDate: Date,
    var userIsSocial: Boolean,
    var role: String,
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
            "",
        )

    constructor(userNickname: String) :
        this(
            0L,
            "",
            "",
            "",
            userNickname,
            "",
            "",
            "",
            0,
            0L,
            0L,
            0L,
            Date(System.currentTimeMillis()),
            false,
            "",
        )

    constructor(userEmail: String, userPw: String) :
        this(
            0L,
            userEmail,
            userPw,
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
            "",
        )

    constructor(userEmail: String, userNickname: String, userLikePet: String) :
        this(
            0L,
            userEmail,
            "",
            "",
            userNickname,
            "",
            "",
            userLikePet,
            0,
            0L,
            0L,
            0L,
            Date(System.currentTimeMillis()),
            false,
            "",
        )
}
