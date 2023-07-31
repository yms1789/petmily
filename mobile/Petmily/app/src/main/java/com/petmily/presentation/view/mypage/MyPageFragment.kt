package com.petmily.presentation.view.mypage

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentMyPageBinding
import com.petmily.presentation.view.MainActivity

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    private lateinit var mainActivity: MainActivity

    private lateinit var myPetAdapter: MyPetAdapter

    private val itemList = mutableListOf<Any>() // 아이템 리스트 (NormalItem과 LastItem 객체들을 추가)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initPetItemList()
        initTabLayout()
    }

    private fun initTabLayout() = with(binding) {
        llMypageBtn.visibility = View.GONE

        tlMypage.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 선택된 탭에 따라 RecyclerView의 가시성을 변경합니다.
                when (tab.position) {
                    0 -> {
                        llMypageBtn.visibility = View.GONE
                    }

                    1 -> {
                        llMypageBtn.visibility = View.GONE
                    }

                    2 -> {
                        llMypageBtn.visibility = View.VISIBLE
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

    private fun initPetItemList() {
        itemList.clear()
        itemList.add(NormalItem("Item 1"))
        itemList.add(NormalItem("Item 2"))
        itemList.add(LastItem("Last Item"))
    }

    private fun initAdapter() = with(binding) {
        myPetAdapter = MyPetAdapter(itemList, ::onNormalItemClick, ::onLastItemClick)
        rcvMypageMypet.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        rcvMypageMypet.adapter = myPetAdapter
    }

    // NormalItem 클릭 이벤트 처리
    private fun onNormalItemClick(normalItem: NormalItem) {
        // TODO: NormalItem 클릭 이벤트 처리 로직 추가
    }

    // LastItem 클릭 이벤트 처리
    private fun onLastItemClick(lastItem: LastItem) {
        // TODO: LastItem 클릭 이벤트 처리 로직 추가
    }
}
