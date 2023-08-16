package com.petmily.repository.api.notification

import android.annotation.SuppressLint
import android.util.Log
import com.petmily.repository.dto.FcmToken
import com.petmily.repository.dto.ResponseNotification
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "petmily_NotificationService"

@SuppressLint("LongLogTag")
class NotificationService {
    
    /**
     * FCM 토근 저장
     */
    suspend fun requstSaveToken(fcmToken: FcmToken): String {
        return try {
            return RetrofitUtil.notificationApi.requstSaveToken(fcmToken)
        } catch (e: ConnectException) {
            Log.d(TAG, "requstSaveToken ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requstSaveToken Exception: ${e.message}")
            throw Exception()
        }
    }

    /**
     * API - notification list
     */
    suspend fun requestNotification(userEmail: String): MutableList<ResponseNotification> {
        return try {
            return RetrofitUtil.notificationApi.requestNotification(userEmail)
        } catch (e: ConnectException) {
            Log.d(TAG, "requestNotification ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestNotification Exception: ${e.message}")
            throw Exception()
        }
    }
}
