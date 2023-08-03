package com.petmily.presentation.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.curation.CurationService
import com.petmily.repository.dto.Curation
import com.petmily.repository.dto.CurationResult
import kotlinx.coroutines.launch
import java.net.ConnectException

private const val TAG = "Petmily_CurationViewModel"

@SuppressLint("LongLogTag")
class CurationViewModel : ViewModel() {
    private val curationService: CurationService by lazy { CurationService() }
    
    // All
    private val _curationAllList = MutableLiveData<CurationResult>()
    val curationAllList: LiveData<CurationResult>
        get() = _curationAllList
    
    // 강아지
    private val _curationDogList = MutableLiveData<MutableList<Curation>>()
    val curationDogList: LiveData<MutableList<Curation>>
        get() = _curationDogList
    
    // 고양이
    private val _curationCatList = MutableLiveData<MutableList<Curation>>()
    val curationCatList: LiveData<MutableList<Curation>>
        get() = _curationCatList
    
    // 기타 동물
    private val _curationEtcList = MutableLiveData<MutableList<Curation>>()
    val curationEtcList: LiveData<MutableList<Curation>>
        get() = _curationEtcList

    /**
     * 모든 동물종 curation 수신 
     */
    fun requestCurationData(species: String, mainViewModel: MainViewModel) {
        Log.d(TAG, "requestCurationData: $species")
        viewModelScope.launch {
            try {
                _curationAllList.value = curationService.requestCurationData(species)
                Log.d(TAG, "requestCurationData: ${_curationAllList.value }")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
}
