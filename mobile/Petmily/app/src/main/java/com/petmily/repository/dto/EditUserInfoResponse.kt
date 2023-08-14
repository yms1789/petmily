package com.petmily.repository.dto

data class EditUserInfoResponse(
    var userInfoEditDtoLoginInfo: UserLoginInfoDto = UserLoginInfoDto(),
    var imageUrl: String = "",
)
