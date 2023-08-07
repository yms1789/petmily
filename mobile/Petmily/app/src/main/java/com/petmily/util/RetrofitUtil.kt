package com.petmily.util

import com.petmily.config.ApplicationClass
import com.petmily.repository.api.board.BoardApi
import com.petmily.repository.api.board.CommentApi
import com.petmily.repository.api.certification.join.JoinApi
import com.petmily.repository.api.certification.login.LoginApi
import com.petmily.repository.api.certification.password.PasswordApi
import com.petmily.repository.api.curation.CurationApi
import com.petmily.repository.api.infoInput.pet.PetInfoInputApi
import com.petmily.repository.api.infoInput.user.UserInfoInputApi

class RetrofitUtil {
    companion object {
        val loginApi: LoginApi = ApplicationClass.retrofit.create(LoginApi::class.java)
        val joinApi: JoinApi = ApplicationClass.retrofit.create(JoinApi::class.java)
        val passwordApi: PasswordApi = ApplicationClass.retrofit.create(PasswordApi::class.java)
        val petInfoInputApi: PetInfoInputApi = ApplicationClass.retrofit.create(PetInfoInputApi::class.java)
        val userInfoInputApi: UserInfoInputApi = ApplicationClass.retrofit.create(UserInfoInputApi::class.java)
        val boardApi: BoardApi = ApplicationClass.retrofit.create(BoardApi::class.java)
        val curationApi: CurationApi = ApplicationClass.retrofit.create(CurationApi::class.java)
        val commentApi: CommentApi = ApplicationClass.retrofit.create(CommentApi::class.java)
    }
}
