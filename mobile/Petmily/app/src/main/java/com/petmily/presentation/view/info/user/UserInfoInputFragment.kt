package com.petmily.presentation.view.info.user

import android.os.Bundle
import android.system.Os.bind
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.commit
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentUserInfoInputBinding
import com.petmily.presentation.view.info.pet.PetInfoInputFragment

class UserInfoInputFragment : BaseFragment<FragmentUserInfoInputBinding>(FragmentUserInfoInputBinding::bind, R.layout.fragment_user_info_input) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initImageView()
        initEditText()
        initBtn()
    }

    private fun initImageView() {
    }

    private fun initEditText() {
        binding.apply {
            // 이메일 입력
            etId.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    if (tilId.isErrorEnabled) tilId.isErrorEnabled = false
                }
            })

            // 선호 반려동물
            actFavorAnimal.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_email, species))
        }
    }

    private fun initBtn() {
        binding.apply {
            // 닉네임 중복확인
            btnNicknameDupConfirm.setOnClickListener {
                if (etId.text.isNullOrBlank()) tilId.error = getString(R.string.userinfoinput_error_nickname)

                if (tilId.error.isNullOrBlank()) {
                }
            }

            // 반려동물 정보 입력
            btnInputPet.setOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.frame_layout_main, PetInfoInputFragment())
                    addToBackStack("petInfoInput")
                }
            }

            // 완료
            btnConfirm.setOnClickListener {
            }
        }
    }

    private fun apiNicknameDupCheck() {
    }

    companion object {
        val species = arrayOf(
            "강아지",
            "고양이",
            "기타 동물",
        )
    }
}
