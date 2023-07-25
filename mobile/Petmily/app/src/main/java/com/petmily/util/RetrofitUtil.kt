package com.petmily.util

import com.petmily.config.ApplicationClass
import com.petmily.repository.api.certification.join.JoinApi
import com.petmily.repository.api.certification.login.LoginApi

class RetrofitUtil {
    companion object {
        val loginService: LoginApi = ApplicationClass.retrofit.create(LoginApi::class.java)
        val joinService: JoinApi = ApplicationClass.retrofit.create(JoinApi::class.java)
    }
}
