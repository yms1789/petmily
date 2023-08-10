package com.petmily.repository.api.mypage

import com.petmily.repository.dto.Board
import com.petmily.repository.dto.MypageInfo
import com.petmily.repository.dto.User
import com.petmily.repository.dto.UserProfileResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MypageApi {
    @GET("/profile/{userEmail}")
    suspend fun requestMypageInfo(@Path("userEmail") userEmail: String): MypageInfo

    @POST("/follow/{userId}")
    suspend fun followUser(
        @Path("userId") userId: Long,
        @Body user: User,
    )

    @HTTP(method = "DELETE", path = "/follow/{userId}", hasBody = true)
    suspend fun unfollowUser(
        @Path("userId") userId: Long,
        @Body user: User,
    )

    @GET("/profile/{userEmail}/likeboard")
    suspend fun likeBoardList(
        @Path("userEmail") userEmail: String,
        @Query("currentUser") currentUser: String,
    ): List<Board>

    @GET("/profile/{userEmail}/following")
    suspend fun followingList(
        @Path("userEmail") userEmail: String,
        @Query("currentUser") currentUser: String,
    ): List<UserProfileResponse>

    @GET("/profile/{userEmail}/follower")
    suspend fun followerList(
        @Path("userEmail") userEmail: String,
        @Query("currentUser") currentUser: String,
    ): List<UserProfileResponse>
}
