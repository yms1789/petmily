package com.petmily.util

import com.petmily.config.ApplicationClass
import com.petmily.repository.api.certification.login.LoginService

class RetrofitUtil {
    companion object {
        val loginService = ApplicationClass.retrofit.create(LoginService::class.java)
    }
}
