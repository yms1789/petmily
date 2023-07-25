package com.petmily.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.certification.join.JoinService
import com.petmily.repository.api.certification.login.LoginService
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val loginService: LoginService by lazy { LoginService() }
    private val joinService: JoinService by lazy { JoinService() }

    // 로그인 토큰
    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    // 이메일 인증 코드
    private val _isEmailCodeSent = MutableLiveData<Boolean>()
    val isEmailCodeSent: LiveData<Boolean>
        get() = _isEmailCodeSent

    // 이메일 인증 코드 확인
    private val _isEmailCodeChecked = MutableLiveData<Boolean>()
    val isEmailCodeChecked: LiveData<Boolean>
        get() = _isEmailCodeChecked
    
    // 회원가입
    private val _isJoined = MutableLiveData<Boolean>()
    val isJoined: LiveData<Boolean>
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
        try {
            viewModelScope.launch {
                _isEmailCodeSent.value = joinService.sendEmailCode(email)
            }
        } catch (e: Exception) {
            _isEmailCodeSent.value = false
        }
    }

    fun checkEmailCode(code: String, userEmail: String) {
        try {
            viewModelScope.launch {
                _isEmailCodeChecked.value = joinService.checkEmailCode(code, userEmail)
            }
        } catch (e: Exception) {
            _isEmailCodeChecked.value = false
        }
    }
    
    fun join(email: String, password: String) {
        try {
            viewModelScope.launch {
                _isJoined.value = joinService.join(email, password)
            }
        } catch (e: Exception) {
            _isJoined.value = false
        }
    }
}
