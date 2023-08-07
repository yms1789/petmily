package com.petmily.repository.api.infoInput.user

import android.annotation.SuppressLint
import android.util.Log
import com.petmily.repository.dto.User
import com.petmily.repository.dto.UserInfo
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Petmily_UserInfoInputService"

@SuppressLint("LongLogTag")
class UserInfoInputService {

    /**
     * userInfo의 file은 없으면 null 가능
     */
    suspend fun requestEditMyPage(userInfo: UserInfo): Boolean {
        return try {
            RetrofitUtil.userInfoInputApi.requestEditMyPage(userInfo.file, userInfo.userInfoEditDto)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "requestEmailCode ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestEmailCode Exception: ${e.message}")
            false
        }
    }
    
    /**
     * user NickName 중복 체크
     */
    suspend fun requestDupNickNameCheck(user: User): Boolean {
        return try {
            RetrofitUtil.userInfoInputApi.requestDupNickNameCheck(user)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "requestEmailCode ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestEmailCode Exception: ${e.message}")
            false
        }
    }
}
