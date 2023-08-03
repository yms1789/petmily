package com.petmily.presentation.view.info.user

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentUserInfoInputBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.UserViewModel
import com.petmily.util.CheckPermission
import com.petmily.util.GalleryUtil
private const val TAG = "Petmily_UserInfoInputFragment"

@SuppressLint("LongLogTag")
class UserInfoInputFragment : BaseFragment<FragmentUserInfoInputBinding>(FragmentUserInfoInputBinding::bind, R.layout.fragment_user_info_input) {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    
    private lateinit var galleryUtil: GalleryUtil
    private lateinit var checkPermission: CheckPermission
    
    private var nickNameDupCheck = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        galleryUtil = GalleryUtil()
        checkPermission = CheckPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initView()
        initEditText()
        initBtn()
        initObserve()
    }

    private fun init() = with(binding) {
        // 유저 닉네임이 null이면 back 버튼을 제거
        if (ApplicationClass.sharedPreferences.getString("userNickname").isNullOrBlank()) {
            ivBack.visibility = View.GONE
        }

        mainViewModel.setFromGalleryFragment("userInfoInput")
        Log.d(TAG, "mainViewModel: ${mainViewModel.getFromGalleryFragment()}")
    }

    private fun initView() = with(binding) {
        // 프로필 사진 setting
        // TODO: Room에 userProfileImage 갱신 & 서버에 userProfileImage 갱신

        // userInfoInput의 프레그먼트 상태일 때 ->
        if (mainViewModel.getFromGalleryFragment() == "userInfoInput") {
            Glide.with(mainActivity)
                .load(mainViewModel.getSelectProfileImage()) // 내가 선택한 사진이 우선 들어가가있음
                .circleCrop()
                .into(ivUserImage)
        }

        // 프로필 사진 view
        ivUserImage.setOnClickListener {
            if (checkPermission.requestStoragePermission()) { // 갤러리 접근 권한 체크
                if (galleryUtil.getImages(mainActivity, mainViewModel)) { // 갤러리 이미지를 모두 로드 했다면
                    mainActivity.changeFragment("gallery")
                }
            }

            // 선호 반려동물 선택
            actFavorAnimal.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_email, species))
        }

        // 뒤로 가기 (마이페이지)
        ivBack.setOnClickListener {
            mainActivity.changeFragment("my page")
        }
    }

    private fun initEditText() = with(binding) {
        // 닉네임 입력
        etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (tilId.isErrorEnabled) tilId.isErrorEnabled = false
                nickNameDupCheck = false
            }
        })

        // 선호 반려동물
        actFavorAnimal.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_email, species))
    }
    
    private fun initBtn() = with(binding) {
        // 닉네임 중복확인
        btnNicknameDupConfirm.setOnClickListener {
            if (etId.text.isNullOrBlank()) tilId.error = getString(R.string.userinfoinput_error_nickname)

            if (tilId.error.isNullOrBlank()) { // 에러가 없으면(== 닉네임 입력 헀으면) -> 닉네임 중복체크
                userViewModel.requestDupNickNameCheck(etId.text.toString(), mainViewModel)
            }
        }

        // 완료 -> user정보 업데이트
        btnConfirm.setOnClickListener {
            if (nickNameDupCheck) {
                // 이미지 변환
                Log.d(TAG, "userInfoInput select Image: ${mainViewModel.getSelectProfileImage()}")
                val image = galleryUtil.convertImageToBase64(mainViewModel.getSelectProfileImage())
                userViewModel.requestEditMyPage(
                    etId.text.toString(),
                    actFavorAnimal.text.toString(),
                    image,
                    mainViewModel,
                ) 
            } else {
                mainActivity.showSnackbar("닉네임 중복 체크가 필요합니다.")
            }
        }
    }

    private fun initObserve() = with(userViewModel) {
        isCheckNickName.observe(viewLifecycleOwner) {
            nickNameDupCheck = it
        }
    }

    companion object {
        val species = arrayOf(
            "강아지",
            "고양이",
            "기타 동물",
        )
    }
}
