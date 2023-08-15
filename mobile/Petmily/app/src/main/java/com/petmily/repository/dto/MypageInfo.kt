package com.petmily.repository.dto

data class MypageInfo(
    var userId: Long,
    var userEmail: String,
    var userNickname: String,
    var userProfileImg: String,
    var followingCount: Int,
    var followerCount: Int,
    var boardCount: Int,
    var userPets: MutableList<Pet>,
) {
    constructor() :
        this(
            0L,
            "",
            "",
            "",
            0,
            0,
            0,
            mutableListOf(),
        )
}
