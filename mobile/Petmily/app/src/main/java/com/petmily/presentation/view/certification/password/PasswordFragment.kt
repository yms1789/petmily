package com.petmily.presentation.view.certification.password

import android.content.Context
import android.os.Bundle
import android.view.View
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPasswordBinding
import com.petmily.presentation.view.MainActivity

class PasswordFragment :
    BaseFragment<FragmentPasswordBinding>(FragmentPasswordBinding::bind, R.layout.fragment_password) {

    val TAG = "Fetmily_PasswordFragment"
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
    }
}
