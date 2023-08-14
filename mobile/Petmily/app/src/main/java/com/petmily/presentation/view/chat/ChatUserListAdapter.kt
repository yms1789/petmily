package com.petmily.presentation.view.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.databinding.ItemChatUserListBinding
import com.petmily.repository.dto.ChatListResponse
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

class ChatUserListAdapter(
    private var chatList: MutableList<ChatListResponse> = mutableListOf(),
) : RecyclerView.Adapter<ChatUserListAdapter.ChatUserListViewHolder>() {

    inner class ChatUserListViewHolder(val binding: ItemChatUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(chat: ChatListResponse) = with(binding) {
            chat.participants[0].apply { // todo 인덱스 0이 상대방인지 확인 필요
                Glide.with(itemView)
                    .load(userProfile)
                    .circleCrop()
                    .into(ivUserImage)

                tvName.text = userNickname
            }

            tvRecentMsg.text = chat.latestMessage
            tvUnread.text = chat.unreadMessageCount.toString()

            val diffTime = timeDifference(chat.createdAt)
            tvSendTime.text = diffTime

            root.setOnClickListener {
                chatUserListClickListener.chatUserListClick(binding, chat, layoutPosition)
            }
        }
    }

    /**
     * 현재시간 - 마지막 채팅 시간
     */
    fun timeDifference(time: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        val inputDateTime = dateFormat.parse(time)

        val timeDifferenceInMillis = abs(currentTime.time - inputDateTime.time)
        val days = timeDifferenceInMillis / (24 * 60 * 60 * 1000)
        val hours = (timeDifferenceInMillis / (60 * 60 * 1000)) % 24
        val minutes = (timeDifferenceInMillis / (60 * 1000)) % 60

        val timeDiff = when {
            days > 0 -> "$days 일 전"
            hours > 0 -> "$hours 시간 전"
            minutes > 0 -> "$minutes 분 전"
            else -> "방금 전"
        }

        return timeDiff
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
