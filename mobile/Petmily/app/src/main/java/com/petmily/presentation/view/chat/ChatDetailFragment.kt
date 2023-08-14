package com.petmily.presentation.view.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentChatDetailBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.ChatViewModel

private const val TAG = "petmily_ChatDetailFragment"

@SuppressLint("LongLogTag")
class ChatDetailFragment :
    BaseFragment<FragmentChatDetailBinding>(FragmentChatDetailBinding::bind, R.layout.fragment_chat_detail) {

    private val mainActivity by lazy {
        context as MainActivity
    }

    private val chatViewModel: ChatViewModel by activityViewModels()
    private lateinit var chatDetailAdapter: ChatDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "hdh -> onViewCreated: ChatDeatailFragment")
        initApi()
        initAdapter()
        initDialog()
        initBtn()
        initObserve()
    }

    private fun initApi() {
    }

    private fun initAdapter() = with(binding) {
        chatDetailAdapter = ChatDetailAdapter(
            chatViewModel.currentChatOther, // 상대방
            ApplicationClass.sharedPreferences.getString("userEmail")!!, // 나
        )

        rcvChatList.apply {
            adapter = chatDetailAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initDialog() = with(binding) {
        etChatMsg.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (etChatMsg.length() > 0 && tvChatSend.visibility == View.GONE) {
                    tvChatSend.visibility = View.VISIBLE
                } else if (etChatMsg.length() == 0) {
                    tvChatSend.visibility = View.GONE
                }
            }
        })
    }

    private fun initBtn() = with(binding) {
        // 뒤로가기 버튼 클릭
        ivBack.setOnClickListener {
            chatViewModel.apply {
                initChatContent() // 채팅 내용 초기화
                initChattingRoomId() // 채팅방 Id 초기화
                disconnectStomp() // Stomp 연결 종료
            }
            parentFragmentManager.popBackStack()
        }

        tvChatSend.setOnClickListener {
            chatViewModel.sendStomp(etChatMsg.text.toString())
            etChatMsg.setText("")
//            etChatMsg.clearFocus()
        }
    }

    private fun initObserve() = with(chatViewModel) {
        // 채팅 룸 아이디
        resultChatRoomId.observe(viewLifecycleOwner) {
            Log.d(TAG, "resultChatRoomId observe")
            // 룸 아이디가 들어오면 Stomp 연결 요청
            runStomp()
        }

        // 채팅 내용
        resultChatContent.observe(viewLifecycleOwner) {
            chatDetailAdapter.submitChat(it)
            binding.rcvChatList.scrollToPosition(it.size - 1)
        }
    }

    // Stomp 연결 종료
    override fun onDestroy() = with(chatViewModel) {
        disconnectStomp()
        super.onDestroy()
    }
}
