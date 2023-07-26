package com.petmily.presentation.view.curation

import android.content.Context
import android.os.Bundle
import android.view.View
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentCurationMainBinding
import com.petmily.presentation.view.MainActivity

class CurationMainFragment :
    BaseFragment<FragmentCurationMainBinding>(FragmentCurationMainBinding::bind, R.layout.fragment_curation_main) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
