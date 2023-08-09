package com.petmily.presentation.view.store

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentInventoryBinding
import com.petmily.presentation.view.MainActivity

class InventoryFragment :
    BaseFragment<FragmentInventoryBinding>(FragmentInventoryBinding::bind, R.layout.fragment_inventory) {

    private lateinit var mainActivity: MainActivity
    private lateinit var shopAdapter: ShopAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        initAdapter()
    }

    private fun initAdapter() = with(binding) {
        shopAdapter = ShopAdapter()

        rcvShop.apply {
            adapter = shopAdapter
            layoutManager = GridLayoutManager(mainActivity, 3, GridLayoutManager.VERTICAL, false)
        }
        //        pointLogAdapter.setChats()
    }
}
