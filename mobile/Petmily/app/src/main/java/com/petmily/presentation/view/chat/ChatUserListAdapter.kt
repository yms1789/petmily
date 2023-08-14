package com.petmily.presentation.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.databinding.ItemChatUserListBinding
import com.petmily.repository.dto.ChatListResponse

class ChatUserListAdapter(
    private var chatList: MutableList<ChatListResponse> = mutableListOf(),
) : RecyclerView.Adapter<ChatUserListAdapter.ChatUserListViewHolder>() {
    
    inner class ChatUserListViewHolder(val binding: ItemChatUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(chat: ChatListResponse) = with(binding) {
            chat.participants[0].apply { // todo 인덱스 0이 상대방인지 확인 필요
                Glide.with(itemView)
                    .load(userProfileImg)
                    .circleCrop()
                    .into(ivUserImage)
    
                tvName.setText(userNickname)
            }
            
            tvRecentMsg.setText(chat.latestMessage)
            tvUnread.setText(chat.unreadMessageCount.toString())
            
            root.setOnClickListener {
                chatUserListClickListener.chatUserListClick(binding, chat, layoutPosition)
            }
        }
    }
    
    // chat data를 갱신
    fun submitChatList(chatList: MutableList<ChatListResponse>) {
        this.chatList = chatList
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserListViewHolder {
        return ChatUserListViewHolder(ItemChatUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: ChatUserListViewHolder, position: Int) {
        holder.bindInfo(chatList[position])
    }
    
    override fun getItemCount(): Int {
        return chatList.size
//        return 10
    }

    // 이벤트 처리 listener
    interface ChatUserListClickListener {
        fun chatUserListClick(binding: ItemChatUserListBinding, chat: ChatListResponse, position: Int)
    }
    private lateinit var chatUserListClickListener: ChatUserListClickListener
    fun setChatUserListClickListener(chatUserListClickListener: ChatUserListClickListener) {
        this.chatUserListClickListener = chatUserListClickListener
    }
}
