package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.certification.join.JoinService
import com.petmily.repository.api.certification.login.LoginService
import com.petmily.repository.dto.Pet
import com.petmily.repository.dto.User
import kotlinx.coroutines.launch

private const val TAG = "Fetmily_UserViewModel"
class UserViewModel : ViewModel() {
    private val loginService: LoginService by lazy { LoginService() }
    private val joinService: JoinService by lazy { JoinService() }
    

    // 로그인 토큰
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    // 이메일 인증 코드
    private val _emailCode = MutableLiveData<String>()
    val emailCode: LiveData<String>
        get() = _emailCode

    // 이메일 인증 코드 확인
    private val _isEmailCodeChecked = MutableLiveData<String>()
    val isEmailCodeChecked: LiveData<String>
        get() = _isEmailCodeChecked

    // 회원가입
    private val _isJoined = MutableLiveData<Boolean>()
    val isJoined: LiveData<Boolean>
        get() = _isJoined

    // Pet 정보 입력 List
    var petInfoList: MutableList<Pet> = mutableListOf()

    fun addPetInfo(pet: Pet) {
        petInfoList.add(pet)
    }

    fun getPetInfo(): MutableList<Pet> {
        return petInfoList
    }

    fun login(email: String, pwd: String) {
        try {
            viewModelScope.launch {
                _user.value = loginService.login(email, pwd)
            }
        } catch (e: Exception) {
            _user.value = User()
        }
    }

    fun sendEmailAuth(email: String) {
        try {
            viewModelScope.launch {
                Log.d(TAG, "sendEmailAuth: $email")
                _emailCode.value = joinService.sendEmailCode(email)
                Log.d(TAG, "sendEmailAuth: ${_emailCode.value}")
            }
        } catch (e: Exception) {
            _emailCode.value = ""
        }
    }

    fun checkEmailCode(code: String, userEmail: String) {
        try {
            viewModelScope.launch {
                _isEmailCodeChecked.value = joinService.checkEmailCode(code, userEmail)
            }
        } catch (e: Exception) {
            _isEmailCodeChecked.value = ""
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
