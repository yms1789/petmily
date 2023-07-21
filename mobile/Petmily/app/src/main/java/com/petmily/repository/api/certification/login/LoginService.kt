package com.petmily.repository.api.certification.login

import com.petmily.repository.dto.User
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("")
    suspend fun login(@Body body: User): User
}
