package com.petmily.presentation.view.info.pet

import android.content.Context
import android.os.Bundle
import android.view.View
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPetInfoInputBinding

class PetInfoInputFragment :
    BaseFragment<FragmentPetInfoInputBinding>(FragmentPetInfoInputBinding::bind, R.layout.fragment_pet_info_input) {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
