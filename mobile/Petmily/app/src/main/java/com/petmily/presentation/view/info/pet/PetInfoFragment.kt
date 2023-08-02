package com.petmily.presentation.view.info.pet

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPetInfoBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel

class PetInfoFragment :
    BaseFragment<FragmentPetInfoBinding>(FragmentPetInfoBinding::bind, R.layout.fragment_pet_info) {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
