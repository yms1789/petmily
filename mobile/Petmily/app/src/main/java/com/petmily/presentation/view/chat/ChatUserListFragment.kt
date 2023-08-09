package com.petmily.presentation.view.chat

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentChatUserListBinding
import com.petmily.databinding.ItemChatUserListBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.repository.dto.Chat

class ChatUserListFragment :
    BaseFragment<FragmentChatUserListBinding>(FragmentChatUserListBinding::bind, R.layout.fragment_chat_user_list) {
    
    private val mainActivity by lazy {
        context as MainActivity
    }
    
    private lateinit var chatUserListAdapter: ChatUserListAdapter
    
    // 임시 데이터
    private val chats: List<Chat> = listOf(Chat(), Chat(), Chat(), Chat(), Chat(), Chat(), Chat(), Chat(), Chat(), Chat())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtn()
        initAdapter()
        initBackPressEvent()
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
                    chat: Chat,
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
        chatUserListAdapter.setChats(chats)
    }
}
