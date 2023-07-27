package com.petmily.presentation.view.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentHomeBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.repository.dto.Curation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.math.ceil

private const val TAG = "Fetmily_HomeFragment"
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity
    
    private lateinit var homeCurationAdapter: HomeCurationAdapter
    private lateinit var boardAdapter: BoardAdapter
    
    private lateinit var curationJob: Job
    
    private val tmpCurationList =
        listOf(
            Curation(curationTitle = "title1"),
            Curation(curationTitle = "title2"),
            Curation(curationTitle = "title3"),
            Curation(curationTitle = "title4"),
            Curation(curationTitle = "title5"),
        )
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCurations()
        initViewPager()
    }
    
    override fun onResume() {
        super.onResume()
        curationJobCreate()
    }

    override fun onPause() {
        super.onPause()
        curationJob.cancel()
    }

    private fun initAdapter() = with(binding) {
        homeCurationAdapter = HomeCurationAdapter().apply {
            // TODO: 클릭 이벤트 처리
        }
        boardAdapter = BoardAdapter().apply {
            // TODO: 클릭 이벤트 처리
        }
        
        vpCuration.apply {
            adapter = homeCurationAdapter
        }
        rcvBoard.apply {
            adapter = boardAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
    
    private fun initCurations() {
        homeCurationAdapter.setCurations(tmpCurationList)
    }
    
    private fun initViewPager() = with(binding) {
        vpCuration.setCurrentItem(1, false)
        
        vpCuration.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                ciCuration.animatePageSelected((vpCuration.currentItem + tmpCurationList.size - 1) % tmpCurationList.size)
                when (vpCuration.currentItem) {
                    homeCurationAdapter.itemCount - 1 -> vpCuration.setCurrentItem(1, false)
                    0 -> vpCuration.setCurrentItem(homeCurationAdapter.itemCount - 2, false)
                }
                
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        if (!curationJob.isActive) curationJobCreate()
                    }
    
                    ViewPager2.SCROLL_STATE_DRAGGING -> curationJob.cancel()
    
//                    ViewPager2.SCROLL_STATE_SETTLING -> {}
                }
            }
        })
        
        ciCuration.apply {
            createIndicators(tmpCurationList.size, 0)
        }
    }
    
    private fun curationJobCreate() {
        curationJob = lifecycleScope.launchWhenResumed {
            delay(1500)
            binding.vpCuration.setCurrentItem(binding.vpCuration.currentItem + 1, true)
        }
    }
}
