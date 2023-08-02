package com.petmily.presentation.view.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentNotificationBinding
import com.petmily.databinding.FragmentSearchBinding
import com.petmily.presentation.view.MainActivity

class NotificationFragment :
    BaseFragment<FragmentNotificationBinding>(FragmentNotificationBinding::bind, R.layout.fragment_notification) {
    
    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }
    
    private fun initAdapter() {
    }
}
