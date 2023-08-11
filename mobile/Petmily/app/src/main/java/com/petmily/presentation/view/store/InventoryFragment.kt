package com.petmily.presentation.view.store

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentInventoryBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.ShopViewModel

class InventoryFragment :
    BaseFragment<FragmentInventoryBinding>(FragmentInventoryBinding::bind, R.layout.fragment_inventory) {

    private lateinit var mainActivity: MainActivity
    private lateinit var shopAdapter: InventoryAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val shopViewModel: ShopViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAPI()
        initAdapter()
        initObserve()
    }

    private fun initAPI() {
        // API 요청 - my 인벤토리 아이템
        shopViewModel.requestMyInventory(mainViewModel)
    }

    private fun initObserve() = with(shopViewModel) {
        resultMyItems.observe(viewLifecycleOwner) {
            shopAdapter.setMyItemList(it)
            shopAdapter.notifyDataSetChanged()
        }
    }

    private fun initAdapter() = with(binding) {
        shopAdapter = InventoryAdapter()

        rcvShop.apply {
            adapter = shopAdapter
            layoutManager = GridLayoutManager(mainActivity, 3, GridLayoutManager.VERTICAL, false)
        }
        //        pointLogAdapter.setChats()
    }
}
