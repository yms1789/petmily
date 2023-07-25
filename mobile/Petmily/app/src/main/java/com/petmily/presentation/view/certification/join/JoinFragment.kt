package com.petmily.presentation.view.certification.join

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentJoinBinding
import com.petmily.presentation.view.MainActivity
import java.util.regex.Pattern

class JoinFragment :
    BaseFragment<FragmentJoinBinding>(FragmentJoinBinding::bind, R.layout.fragment_join) {

    lateinit var mainActivity: MainActivity
    val TAG = "Fetmily_JoinFragment"
    var emailCheck: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // email 선택 adapter
        val emailAdapter = ArrayAdapter(mainActivity, R.layout.dropdown_email, emailList)
        binding.actEmail.setAdapter(emailAdapter)

        // 버튼 이벤트
        binding.apply {
            val checkBoxList = listOf(cbAgree1, cbAgree2, cbAgree3, cbAgree4)

            // 전체동의 checkBox
            cbAgreeAll.setOnCheckedChangeListener { buttonView, isChecked ->
                checkBoxList.forEach { checkBox ->
                    checkBox.isChecked = isChecked
                }
            }

            // 가입하기 버튼
            btnSignup.setOnClickListener {
                val joinCheck = checkJoin()
            }
        }

        emailClickEvent()
        passwordInputEvent()
        passwordCheckEvent()
        initImageView()
    }

    // 최종 가입하기 Check
    private fun checkJoin(): Boolean {
        binding.apply {
            val inputTextConfirm = etPasswordConfirm.text?.toString()?.trim() ?: ""
            val inputText = etPassword.text?.toString()?.trim() ?: ""
            val joinCheckBoxList = listOf(cbAgree1, cbAgree2, cbAgree3, cbAgree4)
            val agreeCheck = joinCheckBoxList.all { it.isChecked }

            if (inputTextConfirm != inputText) {
                Toast.makeText(mainActivity, "비밀번호를 다시 입력해 주세요.", Toast.LENGTH_SHORT).show()
            } else if (!emailCheck) {
                Toast.makeText(mainActivity, "이메일 인증이 필요합니다.", Toast.LENGTH_SHORT).show()
            } else if (!agreeCheck) {
                Toast.makeText(mainActivity, "필수 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
            } else {
                return true
            }
        }

        return false
    }

    // 이메일 인증 요청 처리
    private fun emailClickEvent() {
        binding.apply {
            val checkBoxList = listOf(cbAgree1, cbAgree2, cbAgree3, cbAgree4)

            // 이메일 확인 버튼
            btnEmailAuth.setOnClickListener {
                // 이메일 정보가 제데로 입력 됐을때 -> 인증 요청 API 실행 -> 결과가 올바르면(인증됬으면 emailCheck를 true로)
                val check = checkEmail()

                if (check) {
                    layoutAuthcode.visibility = View.VISIBLE
                    Log.d(TAG, "onViewCreated: 이메일 요청 ok")
                } else {
                    Toast.makeText(mainActivity, "잘못된 형식의 이메일 입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 이메일 입력 Check
    private fun checkEmail(): Boolean {
        binding.apply {
            if (etEmail.text.isNullOrBlank() || actEmail.text.isNullOrBlank()) {
                return false
            } else {
                val email = etEmail.text.toString() + "@" + actEmail.text.toString()
                val pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
                return pattern.matcher(email).matches()
            }
        }
    }

    // 비밀번호 입력 처리
    private fun passwordInputEvent() {
        binding.apply {
            // 비밀번호
            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    val inputText = etPassword.text?.toString()?.trim() ?: ""

                    if (inputText.isNullOrBlank()) {
                        tilPassword.error = "비밀번호를 입력해 주세요."
                    } else if (inputText.length < 8) {
                        tilPassword.error = "비밀번호는 8자 이상으로 이루어져야 합니다."
                    } else {
                        // 정규 표현식에 매칭되는지 확인
                        val pattern =
                            Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
                        val matcher = pattern.matcher(inputText)
                        if (matcher.matches()) {
                            // 유효한 비밀번호 형식일 때
                            tilPassword.isErrorEnabled = false
                        } else {
                            tilPassword.error = "비밀번호는 숫자, 영어, 특수문자로만 이루어져야 합니다."
                        }
                    }
                }
            })
        }
    }

    // 비밀번호 확인 처리
    private fun passwordCheckEvent() {
        binding.apply {
            etPasswordConfirm.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    val inputTextConfirm = etPasswordConfirm.text?.toString()?.trim() ?: ""
                    val inputText = etPassword.text?.toString()?.trim() ?: ""

                    Log.d(TAG, "afterTextChanged: $inputText / $inputTextConfirm")
                    if (inputTextConfirm == inputText) {
                        tilPasswordConfirm.isErrorEnabled = false
                    } else {
                        tilPasswordConfirm.error = "일치하지 않습니다."
                    }
                }
            })
        }
    }

    private fun initImageView() = with(binding) {
        // back
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        val emailList = arrayOf(
            "naver.com",
            "gmail.com",
            "yahoo.com",
            "nate.com",
            "hanmail.com",
        )
    }
}
