package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.infoInput.pet.PetInfoInputService
import com.petmily.repository.dto.Pet
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.net.ConnectException

private const val TAG = "Fetmily_PetViewModel"
class PetViewModel : ViewModel() {
    private val petInfoInputService: PetInfoInputService by lazy { PetInfoInputService() }
    
    // 반려동물 정보 등록 통신 결과
    private val _isPetSaved = MutableLiveData<Boolean>()
    val isPetSaved: LiveData<Boolean>
        get() = _isPetSaved
    
    /**
     * 반려동물 정보 저장
     */
    fun savePetInfo(file: MultipartBody.Part, pet: Pet, mainViewModel: MainViewModel) {
        Log.d(TAG, "savePetInfo: 반려동물 정보 저장")
        viewModelScope.launch {
            try {
                _isPetSaved.value = petInfoInputService.petSave(file, pet)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
}
