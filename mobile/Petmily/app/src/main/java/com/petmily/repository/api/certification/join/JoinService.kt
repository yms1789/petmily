package com.petmily.repository.api.certification.join

import com.petmily.repository.dto.EmailCode
import com.petmily.repository.dto.User
import com.petmily.util.RetrofitUtil

private const val TAG = "Fetmily_JoinService"
class JoinService {
    suspend fun sendEmailCode(userEmail: String): String {
        return RetrofitUtil.joinService.sendEmailCode(EmailCode(userEmail))
    }

    suspend fun checkEmailCode(code: String, userEmail: String): String {
        return RetrofitUtil.joinService.checkEmailCode(EmailCode(code, userEmail))
    }

    suspend fun join(email: String, password: String): Boolean {
        return RetrofitUtil.joinService.signup(User(email, password))
    }
}
