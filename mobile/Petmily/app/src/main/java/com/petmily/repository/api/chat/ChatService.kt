package com.petmily.repository.api.chat

import android.util.Log
import com.petmily.repository.dto.MypageInfo
import com.petmily.repository.dto.SenderReceiver
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Permily_ChatService"
class ChatService {

    /**
     * API - 채팅방 생성
     */
    suspend fun createChat(senderReceiver: SenderReceiver): String {
        return try {
            return RetrofitUtil.chatApi.createChat(senderReceiver)
        } catch (e: ConnectException) {
            Log.d(TAG, "createChat ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "createChat Exception: ${e.message}")
            throw Exception()
        }
    }

    /**
     * API - 해당 채팅방 내용 GET
     */
    suspend fun requestChatData(senderReceiver: SenderReceiver): String { // todo 반환 값 수정 필요
        return try {
            return RetrofitUtil.chatApi.requestChatData(senderReceiver)
        } catch (e: ConnectException) {
            Log.d(TAG, "requestChatData ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestChatData Exception: ${e.message}")
            throw Exception()
        }
    }

    /**
     * API - 채팅방 전체 목록 GET
     */
    suspend fun requestChatList(userEmail: String): String { // todo 반환 값 수정 필요
        return try {
            return RetrofitUtil.chatApi.requestChatList(userEmail)
        } catch (e: ConnectException) {
            Log.d(TAG, "requestChatList ConnectException: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "requestChatList Exception: ${e.message}")
            throw Exception()
        }
    }
}
