package com.petmily.presentation.view.dialog

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.petmily.databinding.DialogFollowerListBinding
import com.petmily.databinding.ItemSearchUserBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.view.search.SearchUserAdapter
import com.petmily.repository.dto.User

class FollowerDialog(
    private val mainActivity: MainActivity,
) : BottomSheetDialog(mainActivity) {
    
    private val binding by lazy {
        DialogFollowerListBinding.inflate(layoutInflater)
    }
    
    private lateinit var followerAdapter: SearchUserAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    
        // Dialog 자체 window가 wrap_content이므로 match_parent로 변경
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
    
    private fun initAdapter() {
        followerAdapter = SearchUserAdapter().apply {
            setUserClickListener(object : SearchUserAdapter.UserClickListener {
                override fun userClick(binding: ItemSearchUserBinding, user: User, position: Int) {
                    // TODO("Not yet implemented")
                }
            })
        }
        binding.rcvFollowerList.apply {
            adapter = followerAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
    
    fun showFollowerDialog() {
        initAdapter()
        
        show()
    }
}
