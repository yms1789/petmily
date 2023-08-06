package com.petmily.presentation.view.info.pet

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPetInfoInputBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.PetViewModel
import com.petmily.repository.dto.Pet
import com.petmily.util.CheckPermission
import com.petmily.util.GalleryUtil

class PetInfoInputFragment :
    BaseFragment<FragmentPetInfoInputBinding>(FragmentPetInfoInputBinding::bind, R.layout.fragment_pet_info_input) {

    private val TAG = "Fetmily_PetInfoInput"
    private lateinit var mainActivity: MainActivity
    private lateinit var galleryUtil: GalleryUtil
    private lateinit var checkPermission: CheckPermission

    // ViewModel
    private val mainViewModel: MainViewModel by activityViewModels()
    private val petViewModel: PetViewModel by activityViewModels()
    
    private var checkGenderStatus = "male"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        galleryUtil = GalleryUtil()
        checkPermission = CheckPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initEditText()
        initButton()
        initView()
        initObserver()
    }

    private fun init() {
        mainViewModel.setFromGalleryFragment("petInfoInput")
    }

    private fun initView() = with(binding) {
        // petInfoInput의 프레그먼트 상태일 때 ->
        if (mainViewModel.getFromGalleryFragment() == "petInfoInput") {
            Glide.with(mainActivity)
                .load(mainViewModel.getSelectProfileImage()) // 내가 선택한 사진이 우선 들어가가있음
                .circleCrop()
                .into(ivPetImage)
        }

        // 앨범 접근 -> 앨범 열기
        ivPetImage.setOnClickListener {
            if (checkPermission.requestStoragePermission()) { // 갤러리 접근 권한 체크
                if (galleryUtil.getImages(mainActivity, mainViewModel)) { // 갤러리 이미지를 모두 로드 했다면
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
            if (isValidInput()) {
                val date = etPetYear.text.toString() + etPetMonth.text.toString() + etPetDay.text.toString()
                
                val pet = Pet(
                    petName = etPetName.text.toString(),
                    petGender = checkGenderStatus,
                    petInfo = etPetIntro.text.toString(),
                    petBirth = date,
                    userEmail = ApplicationClass.sharedPreferences.getString("userEmail") ?: "",
                    speciesName = etPetSpecies.text.toString(),
                )
                
                // TODO: 등록할 pet 정보 삽입
//                petViewModel.savePetInfo(file, pet)
            }
        }
        
        // 생일 모름 체크박스
        cbDontknow.setOnCheckedChangeListener { _, isChecked ->
            changeTextInputLayoutEnable(tilPetYear, etPetYear, !isChecked)
            changeTextInputLayoutEnable(tilPetMonth, etPetMonth, !isChecked)
            changeTextInputLayoutEnable(tilPetDay, etPetDay, !isChecked)
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
    
    private fun initObserver() = with(petViewModel) {
        initIsPetSaved()
        isPetSaved.observe(viewLifecycleOwner) {
            if (it) {
                mainActivity.showSnackbar("반려동물이 성공적으로 등록되었습니다.")
                parentFragmentManager.popBackStack()
            } else {
                mainActivity.showSnackbar("반려동물 등록에 실패하였습니다.")
            }
        }
    }
    
    /**
     * TextInputLayout 입력 가능 불가능 색상 처리
     */
    private fun changeTextInputLayoutEnable(textInputLayout: TextInputLayout, editText: TextInputEditText, changeState: Boolean) {
        if (changeState) {
            val greyColor = ContextCompat.getColorStateList(mainActivity, R.color.grey)
            
            textInputLayout.isEnabled = true
            textInputLayout.defaultHintTextColor = greyColor
            editText.setTextColor(greyColor)
        } else {
            val lightGreyColor = ContextCompat.getColorStateList(mainActivity, R.color.light_grey)
            
            textInputLayout.isEnabled = false
            textInputLayout.defaultHintTextColor = lightGreyColor
            editText.setTextColor(lightGreyColor)
        }
    }
    
    /**
     * 입력이 적절할 경우 true 아닐 경우 false 반환
     * 적절하지 않은 입력에 따라 snackbar 출력
     */
    private fun isValidInput(): Boolean = with(binding) {
        return if (etPetName.text.isNullOrBlank()) {
            mainActivity.showSnackbar("반려동물의 이름을 입력해주세요.")
            false
        } else if (!cbDontknow.isChecked && !isValidBirthInput()) {
            mainActivity.showSnackbar("생일을 다시 확인해주세요.")
            false
        } else {
            true
        }
    }
    
    /**
     * 생일 입력이 합당한지 확인
     */
    private fun isValidBirthInput(): Boolean = with(binding) {
        return if (etPetYear.text.isNullOrBlank() || etPetMonth.text.isNullOrBlank() || etPetDay.text.isNullOrBlank()) {
            // 입력 없음
            false
        } else if (etPetYear.text.toString().length < 2 || etPetMonth.text.toString().length < 2 || etPetDay.text.toString().length < 2) {
            false
        } else if (etPetMonth.text.toString().toInt() > 12 || etPetDay.text.toString().toInt() > 31) {
            false
        } else {
            true
        }
    }
}
