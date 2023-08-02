package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.certification.join.JoinService
import com.petmily.repository.api.certification.login.LoginService
import com.petmily.repository.api.certification.password.PasswordService
import com.petmily.repository.dto.LoginResponse
import com.petmily.repository.dto.Pet
import kotlinx.coroutines.launch
import java.net.ConnectException

private const val TAG = "Fetmily_UserViewModel"

class UserViewModel : ViewModel() {
    private val loginService: LoginService by lazy { LoginService() }
    private val joinService: JoinService by lazy { JoinService() }
    private val passwordService: PasswordService by lazy { PasswordService() }

    var checkSuccessEmail = ""
    
    // 로그인 토큰
    private val _user = MutableLiveData<LoginResponse>()
    val user: LiveData<LoginResponse>
        get() = _user

    // 회원가입 이메일 인증 코드
    private val _joinEmailCode = MutableLiveData<String>()
    val joinEmailCode: LiveData<String>
        get() = _joinEmailCode

    // 회원가입 이메일 인증 코드 확인
    private val _isJoinEmailCodeChecked = MutableLiveData<Boolean>()
    val isJoinEmailCodeChecked: LiveData<Boolean>
        get() = _isJoinEmailCodeChecked

    // 회원가입
    private val _isJoined = MutableLiveData<Boolean>()
    val isJoined: LiveData<Boolean>
        get() = _isJoined

    // 비밀번호 재설정 - 이메일 인증 상태
    private val _pwdEmailCode = MutableLiveData<String>()
    val pwdEmailCode: LiveData<String>
        get() = _pwdEmailCode
    
    // 비밀번호 재설정 - 인증코드 확인
    private val _isPwdEmailCodeChecked = MutableLiveData<Boolean>()
    val isPwdEmailCodeChecked: LiveData<Boolean>
        get() = _isPwdEmailCodeChecked
    
    // 비밀번호 재설정 - 최종 완료 상태
    private val _isChangePassword = MutableLiveData<Boolean>()
    val isChangepPassword: LiveData<Boolean>
        get() = _isChangePassword

    // Pet 정보 입력 List
    var petInfoList: MutableList<Pet> = mutableListOf()

    fun addPetInfo(pet: Pet) {
        petInfoList.add(pet)
    }

    fun getPetInfo(): MutableList<Pet> {
        return petInfoList
    }
    
    fun login(email: String, pwd: String, mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                _user.value = loginService.login(email, pwd)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    // 비밀번호 재설정 - 이메일 인증 코드 요청
    fun sendPassEmailAuth(userEmail: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "sendEmailAuth: 이메일 인증 코드 요청 / userEmail: $userEmail")
        viewModelScope.launch {
            try {
                _pwdEmailCode.value = passwordService.requestEmailCode(userEmail)
                Log.d(TAG, "sendEmailAuth: 인증 코드: ${_joinEmailCode.value}")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    // 비밀번호 재설정 - 이메일 인증 완료 요청
    fun checkPasswordEmailCode(code: String, userEmail: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "checkEmailCode: 이메일 인증 요청 / userEmail: $userEmail, code: ${_joinEmailCode.value}")
        viewModelScope.launch {
            try {
                _isPwdEmailCodeChecked.value = passwordService.checkEmailCode(code, userEmail)
                if (isPwdEmailCodeChecked.value!!) checkSuccessEmail = userEmail
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    // 비밀번호 재설정 - 비밀번호 재설정 완료 버튼(변경된 비밀번호 반환 받음)
    fun changePassword(userEmail: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "Password: userEmail: $userEmail")
        viewModelScope.launch {
            try {
                _isChangePassword.value = passwordService.changePassRequest(userEmail)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

    // 회원가입 - 이메일 인증 코드 요청
    fun sendJoinEmailAuth(userEmail: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "sendEmailAuth: 이메일 인증 코드 요청 / userEmail: $userEmail")
        viewModelScope.launch {
            try {
                _joinEmailCode.value = joinService.requestEmailCode(userEmail) // 서비스 요청
                Log.d(TAG, "sendEmailAuth: 인증 코드: ${_joinEmailCode.value}")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

    // 회원가입 - 이메일 인증 완료 요청
    fun checkJoinEmailCode(code: String, userEmail: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "checkEmailCode: 이메일 인증 요청 / userEmail: $userEmail, code: ${_joinEmailCode.value}")
        viewModelScope.launch {
            try {
                _isJoinEmailCodeChecked.value = joinService.checkEmailCode(code, userEmail)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

    // 회원가입 - 회원가입 완료 버튼
    fun join(userEmail: String, userPw: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "join: 회원가입 / userEmail: $userEmail, userPw: $userPw")
        viewModelScope.launch {
            try {
                _isJoined.value = joinService.join(userEmail, userPw)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
}
