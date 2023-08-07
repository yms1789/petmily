package com.petmily.presentation.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemCommentBinding
import com.petmily.repository.dto.Comment

class ReplyAdapter(
    private var replys: List<Comment> = listOf(Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment()),
) : RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder>() {
    
    inner class ReplyViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(reply: Comment) = with(binding) {
            tvOpenReply.setOnClickListener {
                replyClickListener.replyClick(binding, reply, layoutPosition)
            }
            
            tvOpenReply.visibility = View.GONE
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
        return ReplyViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(
                    parent.context,
                ),
                parent,
                false,
            ),
        )
    }
    
    override fun getItemCount(): Int {
        return replys.size
    }
    
    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
        holder.bindInfo(replys[position])
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setReplys(replys: List<Comment>) {
        this.replys = replys
        notifyDataSetChanged()
    }
    
    // 이벤트 처리 listener
    interface ReplyClickListener {
        fun replyClick(binding: ItemCommentBinding, reply: Comment, position: Int)
    }
    private lateinit var replyClickListener: ReplyClickListener
    fun setReplyClickListener(replyClickListener: ReplyClickListener) {
        this.replyClickListener = replyClickListener
    }
}
