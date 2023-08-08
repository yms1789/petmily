package com.petmily.presentation.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petmily.databinding.ItemSearchUserBinding
import com.petmily.repository.dto.User

class SearchUserAdapter(
    private var users: List<User> = listOf(),
) : RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder>() {
    
    inner class SearchUserViewHolder(val binding: ItemSearchUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(user: User) = with(binding) {
            Glide.with(itemView)
                .load(user.userProfileImg)
                .into(ivProfileImg)
            tvName.text = user.userNickname
            // TODO: 반려동물 리스트 텍스트에 삽입 / tvPets.text =
            
            // 프로필 클릭 시 해당 상세 프로필로 이동
            root.setOnClickListener {
                userClickListener.userClick(binding, user, layoutPosition)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        return SearchUserViewHolder(
            ItemSearchUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }
    
    override fun getItemCount(): Int {
        return users.size
    }
    
    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.bindInfo(users[position])
    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
    
    // 이벤트 처리 listener
    interface UserClickListener {
        fun userClick(binding: ItemSearchUserBinding, user: User, position: Int)
    }
    private lateinit var userClickListener: UserClickListener
    fun setUserClickListener(userClickListener: UserClickListener) {
        this.userClickListener = userClickListener
    }
}
