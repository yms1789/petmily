package com.petmily.repository.api.mypage

import com.petmily.repository.dto.MypageInfo
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MypageApi {

    @GET("/profile/{userEmail}")
    suspend fun requestMypageInfo(@Path("userEmail") userEmail: String): MypageInfo
}
