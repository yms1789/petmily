package com.petmily.repository.api.mypage

import android.util.Log
import com.petmily.repository.dto.MypageInfo
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Petmily_MypageService"
class MypageService {
    /**
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
            return MypageInfo()
        }
    }
}
