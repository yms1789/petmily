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
    private var _isPetSaved = MutableLiveData<Boolean>()
    val isPetSaved: LiveData<Boolean>
        get() = _isPetSaved
    
    // 반려동물 정보 수정 통신 결과
    private var _isPetUpdated = MutableLiveData<Boolean>()
    val isPetUpdated: LiveData<Boolean>
        get() = _isPetUpdated
    
    // 반려동물 정보 삭제 통신 결과
    private var _isPetDeleted = MutableLiveData<Boolean>()
    val isPetDeleted: LiveData<Boolean>
        get() = _isPetDeleted
    
    /**
     * 반려동물 정보 저장 통신
     */
    fun savePetInfo(file: MultipartBody.Part?, pet: Pet, mainViewModel: MainViewModel) {
        Log.d(TAG, "savePetInfo: 반려동물 정보 저장")
        viewModelScope.launch {
            try {
                _isPetSaved.value = petInfoInputService.petSave(file, pet)
                Log.d(TAG, "savePetInfo 결과: ${_isPetSaved.value}")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    /**
     * 반려동물 정보 수정 통신
     */
    fun updatePetInfo(petId: Long, file: MultipartBody.Part?, pet: Pet, mainViewModel: MainViewModel) {
        Log.d(TAG, "updatePetInfo: 반려동물 정보 수정")
        viewModelScope.launch {
            try {
                _isPetUpdated.value = petInfoInputService.petUpdate(petId, file, pet)
                Log.d(TAG, "updatePetInfo 결과: ${_isPetUpdated.value }")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    /**
     * 반려동물 정보 삭제 통신
     */
    fun deletePetInfo(petId: Long, mainViewModel: MainViewModel) {
        Log.d(TAG, "deletePetInfo: 반려동물 정보 삭제")
        viewModelScope.launch {
            try {
                _isPetDeleted.value = petInfoInputService.petDelete(petId)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    fun initIsPetSaved() { _isPetSaved = MutableLiveData<Boolean>() }
    fun initIsPetUpdated() { _isPetUpdated = MutableLiveData<Boolean>() }
    fun initIsPetDeleted() { _isPetDeleted = MutableLiveData<Boolean>() }
}
