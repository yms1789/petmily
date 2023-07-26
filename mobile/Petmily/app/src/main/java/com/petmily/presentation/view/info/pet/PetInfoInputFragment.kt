package com.petmily.presentation.view.info.pet

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPetInfoInputBinding
import com.petmily.presentation.view.MainActivity

class PetInfoInputFragment :
    BaseFragment<FragmentPetInfoInputBinding>(FragmentPetInfoInputBinding::bind, R.layout.fragment_pet_info_input) {

    private val TAG = "Fetmily_PetInfoInput"
    private lateinit var mainActivity: MainActivity
    private var checkGenderStatus = "male"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditText()
        initButton()
        initView()
    }

    private fun initView() = with(binding) {
        // 앨범 접근 -> 앨범 열기
        ivPetImage.setOnClickListener {
        }
    }

    private fun initButton() = with(binding) {
        // 버튼 - [남]
        btnGenderMale.setOnClickListener {
            if (checkGenderStatus == "female") {
                checkGenderStatus = "male"
                btnGenderMale.setBackgroundResource(R.drawable.custom_btn_selected)
                btnGenderFemale.setBackgroundResource(R.drawable.custom_btn_unselected)
            }
        }

        // 버튼 - [여]
        btnGenderFemale.setOnClickListener {
            if (checkGenderStatus == "male") {
                checkGenderStatus = "female"
                Log.d(TAG, "initButton: $checkGenderStatus")
                btnGenderMale.setBackgroundResource(R.drawable.custom_btn_unselected)
                btnGenderFemale.setBackgroundResource(R.drawable.custom_btn_selected)
            }
        }

        // 완료 버튼
        btnPetInputComplete.setOnClickListener {
            if (!etPetName.text.isNullOrBlank()) {
                var date = etPetYear.text.toString() + etPetMonth.text.toString() + etPetDay.text.toString()

//                Pet(
//                    etPetName.text.toString(),
//                    checkGenderStatus,
//                    etPetIntro.text.toString(),
//                    Date(SimpleDateFormat("yyMMdd").parse(date).getTime()),
//
//                )
            }
        }
    }

    private fun initEditText() = with(binding) {
        tilPetName.error = "필수"

        etPetName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                tilPetName.error = null
                if (etPetName.text.isNullOrBlank()) tilPetName.error = "필수"
            }
        })
    }
}
