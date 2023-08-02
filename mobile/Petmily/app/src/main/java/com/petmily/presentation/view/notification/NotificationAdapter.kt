package com.petmily.presentation.view.notification

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemNotificationBinding
import com.petmily.repository.dto.Notification

class NotificationAdapter(
    private var notis: List<Notification> = listOf(),
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {
    
    inner class NotificationViewHolder(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(noti: Notification) = with(binding) {
            root.setOnClickListener {
                notificationClickListener.notificationClick(binding, noti, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
//        holder.bindInfo(notis[position])
    }

    override fun getItemCount(): Int {
//        return notis.size
        return 20
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setNotis(notis: List<Notification>) {
        this.notis = notis
        notifyDataSetChanged()
    }
    
    // 이벤트 처리 listener
    interface NotificationClickListener {
        fun notificationClick(binding: ItemNotificationBinding, noti: Notification, position: Int)
    }
    private lateinit var notificationClickListener: NotificationClickListener
    fun setNotificationListClickListener(notificationClickListener: NotificationClickListener) {
        this.notificationClickListener = notificationClickListener
    }
}
