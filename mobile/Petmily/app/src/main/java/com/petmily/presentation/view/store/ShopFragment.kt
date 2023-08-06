package com.petmily.presentation.view.store

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentShopBinding
import com.petmily.presentation.view.MainActivity

class ShopFragment :
    BaseFragment<FragmentShopBinding>(FragmentShopBinding::bind, R.layout.fragment_shop) {
    
    private lateinit var mainActivity: MainActivity
    
    private lateinit var shopAdapter: ShopAdapter
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initButton()
        initTabLayout()
        initAdapter()
    }
    
    private fun initButton() = with(binding) {
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    
    private fun initTabLayout() = with(binding) {
        tlMypage.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 선택된 탭에 따라 RecyclerView의 가시성을 변경합니다.
                when (tab.position) {
                    0 -> {
                    }
                    
                    1 -> {
                    }
                    
                    2 -> {
                    }
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab) {
                // 선택 해제된 탭에 따라 RecyclerView의 가시성을 변경합니다.
                // 예를 들어, 선택된 탭이 0번 탭이었고 1번 탭으로 변경되는 경우에는 0번 탭의 RecyclerView를 숨깁니다.
                when (tab.position) {
                    0 -> {
                    }
                    
                    1 -> {
                    }
                    
                    2 -> {
                    }
                }
            }
            
            override fun onTabReselected(tab: TabLayout.Tab) {
                // 탭이 이미 선택된 상태에서 다시 선택되었을 때의 동작을 정의합니다.
                // 필요한 경우 이 함수를 구현하십시오.
            }
        })
    }
    
    private fun initAdapter() = with(binding) {
        shopAdapter = ShopAdapter()
    
        rcvShop.apply {
            adapter = shopAdapter
            layoutManager = GridLayoutManager(mainActivity, 3,GridLayoutManager.VERTICAL, false)
        }
//        pointLogAdapter.setChats()
    }
}
