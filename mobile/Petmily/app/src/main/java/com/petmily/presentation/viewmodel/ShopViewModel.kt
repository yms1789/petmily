package com.petmily.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.config.ApplicationClass
import com.petmily.repository.api.shop.ShopService
import com.petmily.repository.dto.RequestItem
import com.petmily.repository.dto.Shop
import kotlinx.coroutines.launch
import java.net.ConnectException

class ShopViewModel : ViewModel() {
    
    private val shopService: ShopService by lazy { ShopService() }
    
    // 아이템 뽑기 결과 수신  -> 수정 필요(반환값 자료형을 아이템 객체로 변환 필요)
    private var _resultItem = MutableLiveData<Shop>()
    val resultItem: LiveData<Shop>
        get() = _resultItem

    /**
     * API - 아이템 뽑기 요청
     */
    fun requestItem(item: String, mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                val userEmail = ApplicationClass.sharedPreferences.getString("userEmail")!!
                // 반환 shop 저장 
                _resultItem.value = shopService.requestItem(
                    RequestItem(
                        userEmail,
                        item,
                    ),
                )
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            } catch (e: Exception) {
                mainViewModel.setConnectException()
            }
        }
    }

    /**
     * API - 내 인벤토리 데이터 요청
     */
    fun requestMyInventory(mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                val userEmail = ApplicationClass.sharedPreferences.getString("userEmail")!!
                // 반환 shop 저장
                shopService.requestMyInventory(userEmail)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            } catch (e: Exception) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    
}
