package com.petmily.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.certification.login.LoginService
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val loginService: LoginService by lazy { LoginService() }

    private val _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    private val _isEmailAuthSended = MutableLiveData<Boolean>()
    val isEmailAuthSended: LiveData<Boolean>
        get() = _isEmailAuthSended

    fun login(email: String, pwd: String) {
        try {
            viewModelScope.launch {
                _token.value = loginService.login(email, pwd)
            }
        } catch (e: Exception) {
            _token.value = ""
        }
    }
}
