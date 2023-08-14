package com.petmily.presentation.view.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentChatDetailBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.ChatViewModel

class ChatDetailFragment :
    BaseFragment<FragmentChatDetailBinding>(FragmentChatDetailBinding::bind, R.layout.fragment_chat_detail) {

    private val mainActivity by lazy {
        context as MainActivity
    }
    
    private val chatViewModel: ChatViewModel by activityViewModels()
    private lateinit var chatDetailAdapter: ChatDetailAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            chatViewModel.currentChatOther,
            ApplicationClass.sharedPreferences.getString("userEmail")!!,
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
            parentFragmentManager.popBackStack()
        }
    }
    
    private fun initObserve() = with(chatViewModel){
        resultChatRoomId.observe(viewLifecycleOwner) {
            // 룸 아이디가 들어오면 Stomp 연결 요청
        }
    }
}
