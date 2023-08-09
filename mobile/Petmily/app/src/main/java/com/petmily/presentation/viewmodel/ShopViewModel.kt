package com.petmily.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShopViewModel : ViewModel() {
    
    // 아이템 뽑기 결과 수신  -> 수정 필요(반환값 자료형을 아이템 객체로 변환 필요)
    private var _resultItem = MutableLiveData<Boolean>()
    val resultItem: LiveData<Boolean>
        get() = _resultItem
    
    // 아이템 뽑기 요청
    
    
    fun setResultItem(result: Boolean) {
        _resultItem.value = result
    }
    
    
}
