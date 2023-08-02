package com.petmily.repository.api.certification.join

import android.util.Log
import com.petmily.repository.dto.EmailCode
import com.petmily.repository.dto.User
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Fetmily_JoinService"
class JoinService {
    suspend fun requestEmailCode(userEmail: String): String {
        return try {
            RetrofitUtil.joinApi.requestEmailCode(EmailCode(userEmail))
        } catch (e: ConnectException) {
            Log.d(TAG, "sendEmailCode: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "sendEmailCode: ${e.message}")
            ""
        }
    }

    suspend fun checkEmailCode(code: String, userEmail: String): Boolean {
        return try {
            RetrofitUtil.joinApi.checkEmailCode(EmailCode(code, userEmail))
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "checkEmailCode: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "checkEmailCode: ${e.message}")
            false
        }
    }

    suspend fun join(userEmail: String, userPw: String): Boolean {
        return try {
            RetrofitUtil.joinApi.signup(User(userEmail, userPw))
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "join: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "join: ${e.message}")
            false
        }
    }
}
