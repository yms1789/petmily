package com.petmily.presentation.view.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.R
import com.petmily.databinding.ItemCommentBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.repository.dto.Comment

class CommentAdapter(
    private val mainActivity: MainActivity,
    private var comments: List<Comment> = listOf(Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(),Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment(), Comment()),
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    
    private lateinit var replyAdapter: ReplyAdapter
    
    inner class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(comment: Comment) = with(binding) {
            tvOpenReply.setOnClickListener {
                if (rcvReply.visibility == View.VISIBLE) {
                    rcvReply.visibility = View.GONE
                    tvOpenReply.text = mainActivity.getString(R.string.comment_tv_open_reply)
                } else {
                    rcvReply.visibility = View.VISIBLE
                    tvOpenReply.text = mainActivity.getString(R.string.comment_tv_close_reply)
                }
//                commentClickListener.commentClick(binding, comment, layoutPosition)
            }
            initView(binding, comment, itemView)
            initAdapter(binding, comment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
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
        return comments.size
    }
    
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindInfo(comments[position])
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setComments(comments: List<Comment>) {
        // 답글이 아닌 일반 댓글만 출력
        this.comments = comments.filter { it.parentId == 0L }
        notifyDataSetChanged()
    }
    
    private fun initView(binding: ItemCommentBinding, comment: Comment, itemView: View) = with(binding) {
        Glide.with(itemView)
            .load(comment.userProfileImg)
            .into(ivProfile)
        
        tvName.text = comment.userNickname
        tvUploadDate.text = comment.commentTime
        tvCommentContent.text = comment.commentContent
    }
    
    private fun initAdapter(binding: ItemCommentBinding, comment: Comment) = with(binding) {
        replyAdapter = ReplyAdapter().apply {
            setReplys(comments.filter { it.parentId == comment.commentId })
        }
        rcvReply.apply {
            adapter = replyAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
    
    // 이벤트 처리 listener
    interface CommentClickListener {
        fun commentClick(binding: ItemCommentBinding, comment: Comment, position: Int)
    }
    private lateinit var commentClickListener: CommentClickListener
    fun setCommentClickListener(commentClickListener: CommentClickListener) {
        this.commentClickListener = commentClickListener
    }
}
