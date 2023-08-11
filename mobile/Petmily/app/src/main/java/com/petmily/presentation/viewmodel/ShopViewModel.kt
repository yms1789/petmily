package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.config.ApplicationClass
import com.petmily.repository.api.shop.ShopService
import com.petmily.repository.dto.InventoryResult
import com.petmily.repository.dto.RequestItem
import com.petmily.repository.dto.Shop
import kotlinx.coroutines.launch
import java.net.ConnectException

private const val TAG = "Petmily_ShopViewModel"
class ShopViewModel : ViewModel() {

    private val shopService: ShopService by lazy { ShopService() }

    // 아이템 뽑기 결과 수신  -> 수정 필요(반환값 자료형을 아이템 객체로 변환 필요)
    private var _resultItem = MutableLiveData<Shop>()
    val resultItem: LiveData<Shop>
        get() = _resultItem

    private var _myInventoryItems = MutableLiveData<InventoryResult>()
    val myInventoryItems: LiveData<InventoryResult>
        get() = _myInventoryItems

    private var _resultMyItems = MutableLiveData<MutableList<Shop>>()
    val resultMyItems: LiveData<MutableList<Shop>>
        get() = _resultMyItems

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
                val myItemResults = shopService.requestMyInventory(userEmail)
                _resultMyItems.value = myItemResults.lingList

                Log.d(TAG, "requestMyInventory: ${_resultMyItems.value}")
            } catch (e: ConnectException) {
                _myInventoryItems.value = InventoryResult()
                mainViewModel.setConnectException()
            } catch (e: Exception) {
                _myInventoryItems.value = InventoryResult()
                mainViewModel.setConnectException()
            }
        }
    }
}
