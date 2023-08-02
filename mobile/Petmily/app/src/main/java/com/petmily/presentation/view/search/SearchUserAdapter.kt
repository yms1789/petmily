package com.petmily.presentation.view.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petmily.databinding.ItemSearchUserBinding
import com.petmily.repository.dto.User

class SearchUserAdapter(
    private var users: List<User> = listOf(),
) : RecyclerView.Adapter<SearchUserAdapter.SearchUserViewHolder>() {
    
    inner class SearchUserViewHolder(val binding: ItemSearchUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindInfo(user: User) = with(binding) {
            // TODO: data binding
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
        return 5
    }
    
    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
//        holder.bindInfo(users[position])
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
