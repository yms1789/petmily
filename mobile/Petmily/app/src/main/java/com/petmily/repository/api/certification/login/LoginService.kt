package com.petmily.repository.api.certification.login

import com.petmily.repository.dto.User
import com.petmily.util.RetrofitUtil

class LoginService {
    suspend fun login(email: String, pwd: String): String {
        return RetrofitUtil.loginService.login(User(email, pwd))
    }
}
