package com.petmily.util

import com.petmily.config.ApplicationClass
import com.petmily.repository.api.certification.login.LoginApi

class RetrofitUtil {
    companion object {
        val loginService = ApplicationClass.retrofit.create(LoginApi::class.java)
    }
}
