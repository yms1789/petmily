package com.petmily.repository.dto

import okhttp3.MultipartBody

data class UserInfo(
    var userInfoEditDto: User,
    var file: MultipartBody.Part?, // 이미지 파일
)
