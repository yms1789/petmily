package com.petmily.repository.api.mypage

import com.petmily.repository.dto.MypageInfo
import com.petmily.repository.dto.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MypageApi {

    // 유저 정보 GET
    @GET("/profile/{userEmail}")
    suspend fun requestMypageInfo(@Path("userEmail") userEmail: String): MypageInfo
    
    // 회원 탈퇴 비밀번호 check
    @GET("/signout/passwordcheck")
    suspend fun requestPasswordCheck(
        @Query("userEmail") userEmail: String,
        @Query("userPw") userPw: String
    ): Boolean
    
    // 회원 탈퇴 요청
    @DELETE("/signout/deleteuser")
    suspend fun requestSignout(@Body body: User): String
}
