package com.petmily.repository.api.certification.join

import android.util.Log
import com.petmily.repository.dto.EmailCode
import com.petmily.repository.dto.User
import com.petmily.util.RetrofitUtil

private const val TAG = "Fetmily_JoinService"
class JoinService {
    suspend fun sendEmailCode(userEmail: String): String {
        return try {
            RetrofitUtil.joinService.sendEmailCode(EmailCode(userEmail))
        } catch (e: Exception) {
            Log.d(TAG, "sendEmailCode: ${e.message}")
            ""
        }
    }

    suspend fun checkEmailCode(code: String, userEmail: String): String? {
        return try {
            RetrofitUtil.joinService.checkEmailCode(EmailCode(code, userEmail))
        } catch (e: Exception) {
            Log.d(TAG, "checkEmailCode: ${e.message}")
            null
        }
    }

    suspend fun join(email: String, password: String): String? {
        return try {
            RetrofitUtil.joinService.signup(User(email, password))
        } catch (e: Exception) {
            Log.d(TAG, "join: ${e.message}")
            null
        }
    }
}
