package com.petmily.presentation.view.store

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.*
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
        initTabLayout()
        initButton()
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

    private fun initTabLayout() = with(binding) {
        TabLayoutMediator(tlShop, vpShop) { tab, position ->
            when (position) {
                0 -> tab.text = "상점"
                1 -> tab.text = "보관함"
            }
        }.attach()
    }

    private fun initButton() = with(binding) {
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}
