package com.petmily.presentation.view.info.pet

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPetInfoInputBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.util.CheckPermission
import com.petmily.util.GalleryUtil

class PetInfoInputFragment :
    BaseFragment<FragmentPetInfoInputBinding>(FragmentPetInfoInputBinding::bind, R.layout.fragment_pet_info_input) {

    private val TAG = "Fetmily_PetInfoInput"
    private lateinit var mainActivity: MainActivity
    private lateinit var galleryUtil: GalleryUtil
    private lateinit var checkPermission: CheckPermission
    private val mainViewModel: MainViewModel by activityViewModels()

    private var checkGenderStatus = "male"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        galleryUtil = GalleryUtil()
        checkPermission = CheckPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditText()
        initButton()
        initView()
    }

    private fun initView() = with(binding) {
        // petInfoInput의 프레그먼트 상태일 때 ->
        if (mainViewModel.getFromGalleryFragment() == "petInfoInput") {
            Glide.with(mainActivity)
                .load(mainViewModel.getSelectProfileImage()) // 내가 선택한 사진이 우선 들어가가있음
                .circleCrop()
                .into(ivPetImage)

            mainViewModel.setFromGalleryFragment("")
        }

        // 앨범 접근 -> 앨범 열기
        ivPetImage.setOnClickListener {
            if (checkPermission.requestStoragePermission()) { // 갤러리 접근 권한 체크
                if (galleryUtil.getImages(mainActivity, mainViewModel)) { // 갤러리 이미지를 모두 로드 했다면
                    mainViewModel.setFromGalleryFragment("petInfoInput")
                    mainActivity.changeFragment("gallery")
                }
            }
        }

        // 뒤로가기 (마이 페이지)
        ivBack.setOnClickListener {
            mainActivity.changeFragment("my page")
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
