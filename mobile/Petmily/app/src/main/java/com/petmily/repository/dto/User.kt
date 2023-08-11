package com.petmily.repository.dto

import java.sql.Date

data class User(
    var userId: Long = 0L,
    var userEmail: String = "",
    var userPw: String = "",
    var userToken: String = "",
    var userNickname: String = "",
    var userRegion: String = "",
    var userProfileImg: String = "",
    var userLikePet: String = "",
    var userPoint: Int = 0,
    var userBadge: Long = 0L,
    var userRing: Long = 0L,
    var userBackground: Long = 0L,
    var userLoginDate: Date = Date(System.currentTimeMillis()),
    var userIsSocial: Boolean = false,
    var role: String = "",
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

    constructor(
        userEmail: String = "",
        userNickname: String = "",
        userPw: String = "",
        userLikePet: String = "",
    ) :
        this(
            0L,
            userEmail,
            userPw,
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
