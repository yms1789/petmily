package com.petmily.presentation.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentBoardDetailBinding
import com.petmily.databinding.FragmentNotificationBinding
import com.petmily.databinding.ItemNotificationBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.repository.dto.Notification

class BoardDetailFragment :
    BaseFragment<FragmentBoardDetailBinding>(FragmentBoardDetailBinding::bind, R.layout.fragment_board_detail) {
    
    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtn()
    }
    
    private fun initBtn() = with(binding) {
        // 뒤로가기 버튼 클릭
//        ivBack.setOnClickListener {
//            parentFragmentManager.popBackStack()
//        }
    }
}
