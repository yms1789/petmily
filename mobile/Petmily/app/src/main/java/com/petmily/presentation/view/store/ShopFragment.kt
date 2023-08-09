package com.petmily.presentation.view.store

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentShopBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.ShopViewModel

class ShopFragment :
    BaseFragment<FragmentShopBinding>(FragmentShopBinding::bind, R.layout.fragment_shop) {

    private lateinit var mainActivity: MainActivity

    private val shopViewModel: ShopViewModel by activityViewModels()

    private lateinit var shopAdapter: ShopAdapter

//    private val fragmentList = mutableListOf(PurchaseFragment(), InventoryFragment())
//    private val titleList = mutableListOf("상점", "보관함")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButton()
        initViewPager()

        initTabLayout()
//        initAdapter()
    }

    private fun initViewPager() = with(binding) {
        var shopViewPagerAdapter = ShopViewPagerAdapter(mainActivity)
        shopViewPagerAdapter.addFragment(PurchaseFragment())
        shopViewPagerAdapter.addFragment(InventoryFragment())

        vpShop.apply {
            adapter = shopViewPagerAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                }
            })
        }
    }

    private fun initButton() = with(binding) {
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initTabLayout() = with(binding) {
        TabLayoutMediator(tlShop, vpShop) { tab, position ->
            when (position) {
                0 -> tab.text = "상점"
                1 -> tab.text = "보관함"
            }
        }.attach()
    }

//    private fun initAdapter() = with(binding) {
//        shopAdapter = ShopAdapter()
//
//        rcvShop.apply {
//            adapter = shopAdapter
//            layoutManager = GridLayoutManager(mainActivity, 3, GridLayoutManager.VERTICAL, false)
//        }
// //        pointLogAdapter.setChats()
//    }
}
