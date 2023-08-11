package com.petmily.repository.api.mypage

import android.util.Log
import com.petmily.repository.dto.MypageInfo
import com.petmily.repository.dto.User
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Petmily_MypageService"
class MypageService {
    /**
     * MainActivity Call
     * UserViewModel Call
     * API - 게시글, 팔로우, 팔로잉, petInfo 불러오기
     */
    suspend fun requestMypageInfo(userEmail: String): MypageInfo {
        return try {
            return RetrofitUtil.mypageApi.requestMypageInfo(userEmail)
        } catch (e: ConnectException) {
            Log.d(TAG, "requestEmailCode ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestEmailCode Exception: ${e.message}")
            throw Exception()
        }
    }
    
    /**
     * API - 비밀번호 확인
     * "userEmail": "string",
     * "userPw": "string"
     */
    suspend fun requestPasswordCheck(user: User): Boolean {
        return try {
            return RetrofitUtil.mypageApi.requestPasswordCheck(user.userEmail, user.userPw)
        } catch (e: ConnectException) {
            Log.d(TAG, "requestEmailCode ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestEmailCode Exception: ${e.message}")
            throw Exception()
        }
    }

    /**
     * API - 회원 탈퇴
     * "userEmail": "string",
     * "userPw": "string"
     */
    suspend fun requestSignout(user: User): String {
        return try {
            Log.d(TAG, "requestSignout User: $user")
            return RetrofitUtil.mypageApi.requestSignout(user)
        } catch (e: ConnectException) {
            Log.d(TAG, "requestSignout ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestSignout Exception: ${e.message}")
            throw Exception()
        }
    }
}
