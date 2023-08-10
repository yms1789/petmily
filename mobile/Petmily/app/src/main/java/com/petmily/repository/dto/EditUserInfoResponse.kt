package com.petmily.repository.dto

data class EditUserInfoResponse(
    var userInfo: User = User(),
    var imageUrl: String = "",
)
