package com.petmily.presentation.view.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentHomeBinding
import com.petmily.presentation.view.MainActivity

private const val TAG = "Fetmily_HomeFragment"
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity
    
    private lateinit var homeCurationAdapter: HomeCurationAdapter
    private lateinit var boardAdapter: BoardAdapter
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
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
}
