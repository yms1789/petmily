package com.petmily.repository.api.infoInput.user

import com.petmily.repository.dto.User
import com.petmily.repository.dto.UserInfo
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface UserInfoInputApi {
    
//    @Multipart
//    @PATCH("/mypage/edit")
//    suspend fun requestEditMyPage(@Body body: UserInfo): String
    
    @Multipart
    @PATCH("/mypage/edit")
    suspend fun requestEditMyPage(@Body body: UserInfo): String
    
    @POST("/nickname/check")
    suspend fun requestDupNickNameCheck(@Body body: User)
}
