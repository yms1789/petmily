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

    // webView Url
    var webViewUrl = ""

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
                val curationResult = curationService.requestCurationData(species)
                _curationAllList.value = curationResult
                Log.d(TAG, "requestCurationData: ${_curationAllList.value }")

                // 이제 cDogList, cCatList, cEtcList에 접근하여 원하는 작업 수행
                _curationDogList.postValue(curationResult.cDogList)
                _curationCatList.postValue(curationResult.cCatList)
//                _curationEtcList.postValue(curationResult.cEtcList)

//                Log.d(TAG, "requestCurationData - Dog List: ${curationDogList.value?.size}")
//                Log.d(TAG, "requestCurationData - Cat List: ${curationCatList.value?.size}")
//                Log.d(TAG, "requestCurationData - Etc List: ${curationEtcList.value}")
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }

//    fun requestCurationData(species: String, mainViewModel: MainViewModel) {
//        Log.d(TAG, "requestCurationData: $species")
//        viewModelScope.launch {
//            try {
//                val curationResult = curationService.requestCurationData(species)
//                _curationAllList.value = curationResult
//
//                // 이제 cDogList, cCatList, cEtcList에 접근하여 원하는 작업 수행
//                val dogList = curationResult.cDogList
//                val catList = curationResult.cCatList
// //                val etcList = curationResult.cEtcList
//
//                Log.d(TAG, "requestCurationData - Dog List: ${dogList.size}")
//                Log.d(TAG, "requestCurationData - Cat List: ${catList.size}")
// //                Log.d(TAG, "requestCurationData - Etc List: ${etcList.size}")
//
//            } catch (e: ConnectException) {
//                mainViewModel.setConnectException()
//            }
//        }
//    }
}
