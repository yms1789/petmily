package com.petmily.util

import com.petmily.config.ApplicationClass
import com.petmily.repository.api.certification.join.JoinApi
import com.petmily.repository.api.certification.login.LoginApi
import com.petmily.repository.api.certification.password.PasswordApi

class RetrofitUtil {
    companion object {
        val loginApi: LoginApi = ApplicationClass.retrofit.create(LoginApi::class.java)
        val joinApi: JoinApi = ApplicationClass.retrofit.create(JoinApi::class.java)
        val passwordApi: PasswordApi = ApplicationClass.retrofit.create(PasswordApi::class.java)
    }
}
