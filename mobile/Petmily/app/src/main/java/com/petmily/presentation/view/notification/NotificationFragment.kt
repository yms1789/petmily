package com.petmily.presentation.view.notification

import android.os.Bundle
import android.system.Os.bind
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentNotificationBinding
import com.petmily.databinding.ItemNotificationBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.BoardViewModel
import com.petmily.repository.dto.Notification

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::bind, R.layout.fragment_notification) {
    
    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }
    
    private lateinit var notificationAdapter: NotificationAdapter
    
    private val boardViewModel: BoardViewModel by activityViewModels()
    
    private val notis: List<Notification> = listOf(
        Notification(), Notification(), Notification(), Notification(), Notification(), Notification(), Notification(), Notification(), Notification(), Notification(), Notification(), Notification(), Notification(),
    )
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initBtn()
        notificationAdapter.setNotis(notis)
    }
    
    private fun initAdapter() = with(binding) {
        notificationAdapter = NotificationAdapter().apply {
            setNotificationListClickListener(object : NotificationAdapter.NotificationClickListener {
                override fun notificationClick(
                    binding: ItemNotificationBinding,
                    noti: Notification,
                    position: Int,
                ) {
                    // TODO: 이동 전에 선택한 board 설정 필요
//                    boardViewModel.selectedBoard =
                    mainActivity.changeFragment("board detail")
                }
            })
        }
        rcvNotiList.apply {
            adapter = notificationAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL))
        }
    }
    
    private fun initBtn() = with(binding) {
        // 뒤로가기 버튼 클릭
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
