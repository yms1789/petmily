package com.petmily.presentation.view.chat

import android.content.Context
import android.os.Bundle
import android.view.View
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentChatUserListBinding
import com.petmily.presentation.view.MainActivity

class ChatUserListFragment :
    BaseFragment<FragmentChatUserListBinding>(FragmentChatUserListBinding::bind, R.layout.fragment_chat_user_list) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
