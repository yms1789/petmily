package com.petmily.presentation.view.chat

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentChatUserListBinding
import com.petmily.databinding.ItemChatUserListBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.ChatViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Chat
import com.petmily.repository.dto.ChatListResponse

class ChatUserListFragment :
    BaseFragment<FragmentChatUserListBinding>(FragmentChatUserListBinding::bind, R.layout.fragment_chat_user_list) {
    
    private val mainActivity by lazy {
        context as MainActivity
    }
    private val mainViewModel: MainViewModel by activityViewModels()
    private val chatViewModel: ChatViewModel by activityViewModels()
    private lateinit var chatUserListAdapter: ChatUserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initApi()
        initObserve()
        initBtn()
        initAdapter()
        initBackPressEvent()
    }
    
    private fun initApi() = with(chatViewModel) {
        // 채팅방 리스트 요청
        requestChatList(mainViewModel)
    }
    
    private fun initObserve() = with(chatViewModel) {
        resultChatList.observe(viewLifecycleOwner) {
            chatUserListAdapter.submitChatList(it)
        }
    }
    
    private fun initBackPressEvent() {
        // 핸드폰 기기 back버튼
        mainActivity.onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mainActivity.bottomNavigationView.selectedItemId = R.id.navigation_page_home
                }
            },
        )
    }
    
    private fun initBtn() = with(binding) {
    }
    
    private fun initAdapter() = with(binding) {
        chatUserListAdapter = ChatUserListAdapter().apply {
            setChatUserListClickListener(object : ChatUserListAdapter.ChatUserListClickListener {
                override fun chatUserListClick(
                    binding: ItemChatUserListBinding,
                    chat: ChatListResponse,
                    position: Int,
                ) {
                    mainActivity.changeFragment("chat detail")
                }
            })
        }
        
        rcvChatUserList.apply {
            adapter = chatUserListAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}
