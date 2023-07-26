package com.petmily.repository.api.certification.login

import com.petmily.repository.dto.LoginResponse
import com.petmily.repository.dto.User
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/login")
    suspend fun login(@Body body: User): LoginResponse
}
