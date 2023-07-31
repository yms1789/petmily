package com.petmily.presentation.view.certification.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentLoginBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.UserViewModel

private const val TAG = "Fetmily_LoginFragment"
class LoginFragment :
    BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::bind, R.layout.fragment_login) {

    private val userViewModel: UserViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEditText()
        initBtn()
        initTextView()
        initObserver()
    }

    private fun initEditText() = with(binding) {
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

    private fun initBtn() = with(binding) {
        // 로그인
        btnLogin.setOnClickListener {
            if (isValidInput()) {
                if (mainActivity.isNetworkConnected()) {
                    userViewModel.login(etId.text.toString(), etPwd.text.toString(), mainViewModel)
                }
            } else {
                if (etId.text.isNullOrBlank()) tilId.error = getString(R.string.login_error_id)
                if (etPwd.text.isNullOrBlank()) tilPwd.error = getString(R.string.login_error_pwd)
            }
        }
    }

    private fun initTextView() = with(binding) {
        // 비밀번호 재설정
        tvPwdFind.setOnClickListener {
            mainActivity.changeFragment("password")
        }

        // 회원가입
        tvSignup.setOnClickListener {
            mainActivity.changeFragment("join")
        }
    }
    
    private fun initObserver() = with(userViewModel) {
        // 로그인
        user.observe(viewLifecycleOwner) {
            if (it.data == null || it.data!!.user == null || it.data!!.user!!.userEmail == "") {
                // 에러, 로그인 실패
                mainActivity.showSnackbar("아이디 비밀번호를 다시 확인하세요.")
            } else {
                // 성공
                // SharedPreference에 저장
                ApplicationClass.sharedPreferences.addUser(it.data!!.user!!)
                
                // 최초 로그인시(닉네임 없음) -> (회원정보 입력창으로 이동)
                if (it.data!!.user!!.userNickname == "") {
                    mainActivity.changeFragment("userInfoInput")
                } else { // home으로
                    mainActivity.changeFragment("home")
                }
            }
        }
        
        // ConnectException
        mainViewModel.connectException.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver: ConnectException")
            mainActivity.showSnackbar("서버 연결에 실패하였습니다.")
        }
    }
    
    // 이메일, 비밀번호 입력이 유효한가
    private fun isValidInput(): Boolean = with(binding) {
        // 이메일, 비밀번호 공백 없음
        return tilId.error.isNullOrBlank() && tilPwd.error.isNullOrBlank()
    }
}
