package com.petmily.presentation.view.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemChatUserListBinding
import com.petmily.repository.dto.Chat

class ChatUserListAdapter(
    private var chats: List<Chat> = listOf(),
) : RecyclerView.Adapter<ChatUserListAdapter.ChatUserListViewHolder>() {
    
    inner class ChatUserListViewHolder(val binding: ItemChatUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(chat: Chat) = with(binding) {
            root.setOnClickListener {
                chatUserListClickListener.chatUserListClick(binding, chat, layoutPosition)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatUserListViewHolder {
        return ChatUserListViewHolder(ItemChatUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: ChatUserListViewHolder, position: Int) {
        holder.bindInfo(chats[position])
    }
    
    override fun getItemCount(): Int {
        return chats.size
//        return 10
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setChats(chats: List<Chat>) {
        this.chats = chats
        notifyDataSetChanged()
    }
    
    // 이벤트 처리 listener
    interface ChatUserListClickListener {
        fun chatUserListClick(binding: ItemChatUserListBinding, chat: Chat, position: Int)
    }
    private lateinit var chatUserListClickListener: ChatUserListClickListener
    fun setChatUserListClickListener(chatUserListClickListener: ChatUserListClickListener) {
        this.chatUserListClickListener = chatUserListClickListener
    }
}
