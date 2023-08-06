package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.config.ApplicationClass
import com.petmily.repository.api.certification.join.JoinService
import com.petmily.repository.api.certification.login.LoginService
import com.petmily.repository.api.certification.password.PasswordService
import com.petmily.repository.api.infoInput.user.UserInfoInputService
import com.petmily.repository.dto.LoginResponse
import com.petmily.repository.dto.Pet
import com.petmily.repository.dto.User
import com.petmily.repository.dto.UserInfo
import kotlinx.coroutines.launch
import java.net.ConnectException

private const val TAG = "Petmily_UserViewModel"

class UserViewModel : ViewModel() {
    private val loginService: LoginService by lazy { LoginService() }
    private val joinService: JoinService by lazy { JoinService() }
    private val passwordService: PasswordService by lazy { PasswordService() }
    private val userInfoInputService: UserInfoInputService by lazy { UserInfoInputService() }

    // 이메일 인증 수신 결과 저장
    var checkSuccessEmail = ""
    
    // userInfo 입력 상태 유지
    var userInfoNickName = ""
    var userInfoPet = ""

    // 로그인 토큰
    private var _user = MutableLiveData<LoginResponse>()
    val user: LiveData<LoginResponse>
        get() = _user

    // 회원가입 이메일 인증 코드
    private var _joinEmailCode = MutableLiveData<String>()
    val joinEmailCode: LiveData<String>
        get() = _joinEmailCode

    // 회원가입 이메일 인증 코드 확인
    private var _isJoinEmailCodeChecked = MutableLiveData<Boolean>()
    val isJoinEmailCodeChecked: LiveData<Boolean>
        get() = _isJoinEmailCodeChecked

    // 회원가입
    private var _isJoined = MutableLiveData<Boolean>()
    val isJoined: LiveData<Boolean>
        get() = _isJoined

    // 비밀번호 재설정 - 이메일 인증 상태
    private var _pwdEmailCode = MutableLiveData<String>()
    val pwdEmailCode: LiveData<String>
        get() = _pwdEmailCode

    // 비밀번호 재설정 - 인증코드 확인
    private var _isPwdEmailCodeChecked = MutableLiveData<Boolean>()
    val isPwdEmailCodeChecked: LiveData<Boolean>
        get() = _isPwdEmailCodeChecked

    // 비밀번호 재설정 - 최종 완료 상태
    private var _isChangePassword = MutableLiveData<Boolean>()
    val isChangepPassword: LiveData<Boolean>
        get() = _isChangePassword
    
    // 유저정보입력 - 닉네임 중복 체크
    private var _isCheckNickName = MutableLiveData<Boolean>()
    val isCheckNickName: LiveData<Boolean>
        get() = _isCheckNickName

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
    
    /**
     *  비밀번호 재설정 - 이메일 인증 코드 요청
     */
    fun sendPassEmailAuth(userEmail: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "sendEmailAuth: 이메일 인증 코드 요청 / userEmail: $userEmail")
        viewModelScope.launch {
            try {
                _pwdEmailCode.value = passwordService.requestEmailCode(userEmail)
                Log.d(TAG, "sendEmailAuth: 인증 코드: ${_pwdEmailCode.value}")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    /**
     *  비밀번호 재설정 - 이메일 인증 완료 요청
     */
    fun checkPasswordEmailCode(code: String, userEmail: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "checkEmailCode: 이메일 인증 요청 / userEmail: $userEmail, code: ${_pwdEmailCode.value}")
        viewModelScope.launch {
            try {
                _isPwdEmailCodeChecked.value = passwordService.checkEmailCode(code, userEmail)
                if (isPwdEmailCodeChecked.value!!) checkSuccessEmail = userEmail
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    /**
     * 비밀번호 재설정 - 비밀번호 재설정 완료 버튼(변경된 비밀번호 반환 받음)
     */
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

    /**
     * 회원가입 - 이메일 인증 코드 요청
     */
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

    /**
     * 회원가입 - 이메일 인증 완료 요청
     */
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

    /**
     * 회원가입 - 회원가입 완료 버튼
     */
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

    /**
     * 유저 정보 편집 (이미지 파일 null 가능)
     */
    fun requestEditMyPage(userNickName: String, userLikePet: String, imageFile: String?, mainViewModel: MainViewModel) {
        Log.d(TAG, "userEmail: ${ ApplicationClass.sharedPreferences.getString("userEmail")} , userInfo: nickName: $userNickName , likePet: $userLikePet , imageFile: $imageFile")
        viewModelScope.launch {
            try {
                val user = User(
                    ApplicationClass.sharedPreferences.getString("userEmail")!!,
                    userNickName,
                    userLikePet,
                )
                userInfoInputService.requestEditMyPage(UserInfo(user, imageFile))
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    /**
     * 유저 정보 입력 전 NickName 중복체크
     */
    fun requestDupNickNameCheck(userNickName: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "requestDupNickNameCheck: nickName: $userNickName")
        viewModelScope.launch {
            try {
                val user = User(userNickName)
                _isCheckNickName.value = userInfoInputService.requestDupNickNameCheck(user)
                Log.d(TAG, "requestDupNickNameCheck: result: ${_isCheckNickName.value}")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

    /**
     * 유저 정보 입력 상태 저장
     */
    fun setUserInfoInputSave(nickName: String, pet: String) {
        userInfoNickName = nickName
        userInfoPet = pet
    }
    
    /**
     * 유저 정보 입력 상태 얻기
     */
    fun getUserInfoInputNickName(): String {
        return userInfoNickName
    }
    fun getUserInfoInputPet(): String {
        return userInfoPet
    }
    
    /**
     * 유저 정보 입력 상태 초기화
     */
    fun clearUserInfo() {
        userInfoNickName = ""
        userInfoPet = ""
    }
    
    fun initUser() { _user = MutableLiveData<LoginResponse>() }
    fun initJoinEmailCode() { _joinEmailCode = MutableLiveData<String>() }
    fun initIsJoinEmailCodeChecked() { _isJoinEmailCodeChecked = MutableLiveData<Boolean>() }
    fun initIsJoined() { _isJoined = MutableLiveData<Boolean>() }
    fun initPwdEmailCode() { _pwdEmailCode = MutableLiveData<String>() }
    fun initIsPwdEmailCodeChecked() { _isPwdEmailCodeChecked = MutableLiveData<Boolean>() }
    fun initIsChangePassword() { _isChangePassword = MutableLiveData<Boolean>() }
    fun initIsCheckNickName() { _isCheckNickName = MutableLiveData<Boolean>() }
}
