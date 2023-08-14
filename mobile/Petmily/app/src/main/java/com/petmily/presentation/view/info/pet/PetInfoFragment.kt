package com.petmily.presentation.view.info.pet

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPetInfoBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.PetViewModel
import com.petmily.presentation.viewmodel.UserViewModel
import com.petmily.repository.dto.Pet

class PetInfoFragment :
    BaseFragment<FragmentPetInfoBinding>(FragmentPetInfoBinding::bind, R.layout.fragment_pet_info) {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val petViewModel: PetViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPetInfo()
        initButton()
        initObserve()
    }

    /**
     * Pet 정보 세팅
     */
    private fun initPetInfo() = with(binding) {
        petViewModel.selectPetInfo.apply {
            Glide.with(mainActivity)
                .load(petImg)
                .into(ivPetInfo)

            tvPetInfoName.text = petName
            tvPetInfoGender.text = if (petGender == "male") "수컷" else "암컷"
            tvPetInfoSpecies.text = speciesName
            tvPetInfoBirthContents.text = petBirth
            tvPetInfoIntroContents.text = petInfo
        }
    }

    private fun initButton() = with(binding) {
        ivBack.setOnClickListener {
            petViewModel.selectPetInfo = Pet()
            parentFragmentManager.popBackStack()
        }

        btnPetInfoModify.setOnClickListener {
            petViewModel.fromPetInfoInputFragment = "PetInfoFragment"
            mainActivity.changeFragment("petInfoInput")
        }

        btnPetInfoDelete.setOnClickListener {
            petViewModel.deletePetInfo(petViewModel.selectPetInfo.petId, mainViewModel)
        }
    }

    private fun initObserve() = with(petViewModel) {
        initIsPetDeleted()

        // 펫 정보 삭제
        isPetDeleted.observe(viewLifecycleOwner) {
            if (it) { // pet 정보가 삭제 성공
                userViewModel.requestMypageInfo(mainViewModel)
                mainActivity.showSnackbar("성공적으로 삭제 되었습니다.")
                parentFragmentManager.popBackStack()
            } else {
                mainActivity.showSnackbar("삭제 실패하였습니다.")
            }
        }
    }
}