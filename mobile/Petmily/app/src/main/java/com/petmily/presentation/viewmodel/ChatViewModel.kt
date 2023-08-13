package com.petmily.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.config.ApplicationClass
import com.petmily.repository.api.chat.ChatService
import com.petmily.repository.dto.SenderReceiver
import kotlinx.coroutines.launch
import java.net.ConnectException

class ChatViewModel : ViewModel() {

    private val chatService: ChatService by lazy { ChatService() }

    private var _resultChatRoomId = MutableLiveData<String>()
    val resultChatRoomId: LiveData<String>
        get() = _resultChatRoomId

    /**
     * API - 채팅방 생성
     */
    fun createChat(otherEmail: String, mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                val userEmail = ApplicationClass.sharedPreferences.getString("userEmail")
                _resultChatRoomId.value = chatService.createChat(
                    SenderReceiver(
                        sender = userEmail!!,
                        receiver = otherEmail,
                    ),
                )
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

    /**
     * API - 해당 채팅방 내용 GET
     */
    fun requestChatData(mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
//                chatService.requestChatData()
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

    /**
     * API - 채팅방 전체 목록 GET
     */
    fun requestChatList(mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                val userEmail = ApplicationClass.sharedPreferences.getString("userEmail")
                chatService.requestChatList(userEmail!!)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
}
