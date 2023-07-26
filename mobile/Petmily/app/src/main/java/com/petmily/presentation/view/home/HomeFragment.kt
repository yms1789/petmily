package com.petmily.presentation.view.home

import android.content.Context
import android.os.Bundle
import android.view.View
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentHomeBinding
import com.petmily.presentation.view.MainActivity

class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
