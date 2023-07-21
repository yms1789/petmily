package com.petmily.presentation.view.certification.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.commit
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentLoginBinding
import com.petmily.presentation.view.certification.join.JoinFragment
import com.petmily.presentation.view.certification.password.PasswordFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "Fetmily_LoginFragment"

class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditText()
        initBtn()
        initTextView()
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

            // 비밀번호 입력
            etPwd.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    if (tilPwd.isErrorEnabled) tilPwd.isErrorEnabled = false
                }
            })
        }
    }

    private fun initBtn() {
        binding.apply {
            // 로그인
            btnLogin.setOnClickListener {
                if (etId.text.isNullOrBlank()) tilId.error = getString(R.string.login_id_error)
                if (etPwd.text.isNullOrBlank()) tilPwd.error = getString(R.string.login_pwd_error)

                if (tilId.error.isNullOrBlank() && tilPwd.error.isNullOrBlank()) {
                    // TODO: 로그인 API 통신
                    CoroutineScope(Dispatchers.IO).launch {
//                        val user = RetrofitUtil.loginService.login(User(etId.text.toString(), etPwd.text.toString()))
                    }
                }
            }
        }
    }

    private fun initTextView() {
        binding.apply {
            // 비밀번호 재설정
            tvPwdFind.setOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.frame_layout_main, PasswordFragment())
                    addToBackStack("loginToPassword")
                }
            }

            // 회원가입
            tvSignup.setOnClickListener {
                parentFragmentManager.commit {
                    replace(R.id.frame_layout_main, JoinFragment())
                    addToBackStack("loginToSignup")
                }
            }
        }
    }
}
