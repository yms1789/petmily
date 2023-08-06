package com.petmily.presentation.view.store

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPointLogBinding
import com.petmily.presentation.view.MainActivity

class PointLogFragment :
    BaseFragment<FragmentPointLogBinding>(FragmentPointLogBinding::bind, R.layout.fragment_point_log) {
    
    private val mainActivity by lazy {
        context as MainActivity
    }
    
    private lateinit var pointLogAdapter: PointLogAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        initButton()
        initAdapter()
    }
    
    private fun initButton() = with(binding) {
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    
    private fun initAdapter() = with(binding) {
        pointLogAdapter = PointLogAdapter()
    
        rcvPointLog.apply {
            adapter = pointLogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
//        pointLogAdapter.setChats()
    }
}
