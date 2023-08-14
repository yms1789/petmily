package com.petmily.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.config.ApplicationClass
import com.petmily.repository.api.chat.ChatService
import com.petmily.repository.dto.Chat
import com.petmily.repository.dto.MypageInfo
import com.petmily.repository.dto.SenderReceiver
import kotlinx.coroutines.launch
import org.json.JSONObject
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.net.ConnectException

private const val TAG = "petmily_ChatViewModel"
class ChatViewModel : ViewModel() {

    private val chatService: ChatService by lazy { ChatService() }

    // 현재 채팅방의 상대방 Email
    var currentChatOther = MypageInfo()
    
    // 채팅 룸 ID
    private var _resultChatRoomId = MutableLiveData<String>()
    val resultChatRoomId: LiveData<String>
        get() = _resultChatRoomId

    // 채팅 룸의 대화 내용
    private var _resultChatContent = MutableLiveData<MutableList<Chat>>()
    val resultChatContent: LiveData<MutableList<Chat>>
        get() = _resultChatContent

    /**
     * API - 채팅방 생성
     */
    fun createChatRoom(otherEmail: String, mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                val userEmail = ApplicationClass.sharedPreferences.getString("userEmail")
                _resultChatRoomId.value = chatService.createChatRoom(
                    SenderReceiver(
                        sender = userEmail!!,
                        receiver = otherEmail,
                    ),
                )
    
                Log.d(TAG, "createChatRoom: ${_resultChatRoomId.value}")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

    /**
     * API - 해당 채팅방 내용 GET
     */
    fun requestChatData(otherEmail: String, mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                val userEmail = ApplicationClass.sharedPreferences.getString("userEmail")
                
                _resultChatContent.value = chatService.requestChatData(
                    SenderReceiver(
                        sender = userEmail!!,
                        receiver = otherEmail,
                    ),
                )
    
                Log.d(TAG, "requestChatData: ${_resultChatContent.value}")
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

    /*
    -------------------------------------------------------------------------
                                STOMP 통신
    -------------------------------------------------------------------------
     */
    
    val url = "ws://i9d209.p.ssafy.io:8081/chatting/websocket"
    val stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
    
    @SuppressLint("CheckResult")
    fun runStomp() {
        // 전달되면 해당 람다 표현식 내의 코드가 실행
        stompClient.topic("/sub/room/$_resultChatRoomId").subscribe { topicMessage ->
            Log.d("message Recieve", topicMessage.payload)
        }

//        val headerList = arrayListOf<StompHeader>()
//        headerList.add(StompHeader("inviteCode", "test0912"))
//        headerList.add(StompHeader("username", "dong"))
//        headerList.add(StompHeader("positionType", "1"))
//        stompClient.connect(headerList)
        
        stompClient.connect()
        
        stompClient.lifecycle().subscribe { lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d("OPENED", "opened")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.d("CLOSED", "closed")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.d("ERROR", "error")
                    Log.i("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else -> {
                    Log.d("else", lifecycleEvent.message)
                }
            }
        }
    }
    
    @SuppressLint("CheckResult")
    fun sendStomp() {
        val data = JSONObject()
        data.put("roomId", "786eba3e-ede6-486f-ad4e-4fc1233aa628")
        data.put("writer", "dong1@naver.com")
        data.put("message", "실시간 채팅 너무 시르다~~")
        
        // 메시지를 보낼 엔드포인트 URL
        val messageSendEndpoint = "/pub/message"
        
        // stompClient.send() 함수로 메시지를 전송
        stompClient.send(messageSendEndpoint, data.toString()).subscribe(
            {
                // 성공시
                Log.d("Message Sent", "Message sent successfully")
            },
            { error ->
                // 실패시
                Log.d("Message Send Error", error.toString())
            },
        )
    }
}
