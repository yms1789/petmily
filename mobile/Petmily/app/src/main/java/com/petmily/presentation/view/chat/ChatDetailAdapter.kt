package com.petmily.presentation.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemChatBinding
import com.petmily.repository.dto.Chat

class ChatDetailAdapter(
    private var chats: List<Chat> = listOf(),
) : RecyclerView.Adapter<ChatDetailAdapter.ChatListViewHolder>() {
    
    inner class ChatListViewHolder(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo() = with(binding) {
        }
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChatListViewHolder {
        return ChatListViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
//        holder.bindInfo()
    }
    
    override fun getItemCount(): Int {
//        return chats.size
        return 10
    }
}