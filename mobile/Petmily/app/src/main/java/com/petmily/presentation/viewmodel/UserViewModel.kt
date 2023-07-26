package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.certification.join.JoinService
import com.petmily.repository.api.certification.login.LoginService
import kotlinx.coroutines.launch

private const val TAG = "Fetmily_UserViewModel"
class UserViewModel : ViewModel() {
    private val loginService: LoginService by lazy { LoginService() }
    private val joinService: JoinService by lazy { JoinService() }

    // 로그인 토큰
    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    // 이메일 인증 코드
    private val _emailCode = MutableLiveData<String>()
    val emailCode: LiveData<String>
        get() = _emailCode

    // 이메일 인증 코드 확인
    private val _isEmailCodeChecked = MutableLiveData<String?>()
    val isEmailCodeChecked: LiveData<String?>
        get() = _isEmailCodeChecked

    // 회원가입
    private val _isJoined = MutableLiveData<String?>()
    val isJoined: LiveData<String?>
        get() = _isJoined

    fun login(email: String, pwd: String) {
        try {
            viewModelScope.launch {
                _token.value = loginService.login(email, pwd)
            }
        } catch (e: Exception) {
            _token.value = ""
        }
    }

    fun sendEmailAuth(email: String) {
        viewModelScope.launch {
            Log.d(TAG, "sendEmailAuth: email: $email, code: ${_emailCode.value}")
            _emailCode.value = joinService.sendEmailCode(email)
        }
    }

    fun checkEmailCode(code: String, userEmail: String) {
        viewModelScope.launch {
            _isEmailCodeChecked.value = joinService.checkEmailCode(code, userEmail)
        }
    }

    fun join(email: String, password: String) {
        viewModelScope.launch {
            _isJoined.value = joinService.join(email, password)
        }
    }
}
