package com.petmily.repository.api.certification.join

import com.petmily.repository.dto.User
import retrofit2.http.Body
import retrofit2.http.POST

interface JoinApi {
    @POST("/signup/email")
    suspend fun validEmail(@Body body: String): Boolean

    @POST("/signup/email/verification")
    suspend fun checkEmailCode(@Body body: String): Boolean

    @POST("/signup")
    suspend fun signup(@Body body: User): Boolean
}
