package com.petmily.repository.api.certification.join

import com.petmily.repository.dto.EmailCode
import com.petmily.repository.dto.User
import com.petmily.util.RetrofitUtil

class JoinService {
    suspend fun sendEmailCode(email: String): Boolean {
        return RetrofitUtil.joinService.sendEmailCode(email)
    }

    suspend fun checkEmailCode(code: String, userEmail: String): Boolean {
        return RetrofitUtil.joinService.checkEmailCode(EmailCode(code, userEmail))
    }

    suspend fun join(email: String, password: String): Boolean {
        return RetrofitUtil.joinService.signup(User(email, password))
    }
}
