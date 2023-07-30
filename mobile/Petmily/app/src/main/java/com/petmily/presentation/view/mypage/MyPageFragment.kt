package com.petmily.presentation.view.mypage

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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

        itemList.clear()
        itemList.add(NormalItem("Item 1"))
        itemList.add(NormalItem("Item 2"))
        itemList.add(LastItem("Last Item"))

        initAdapter()
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
