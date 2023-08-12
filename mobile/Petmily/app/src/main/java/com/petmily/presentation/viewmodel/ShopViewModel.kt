package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.config.ApplicationClass
import com.petmily.repository.api.shop.ShopService
import com.petmily.repository.dto.Equipment
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

    private var _resultAll = MutableLiveData<MutableList<Shop>>()
    val resultAll: LiveData<MutableList<Shop>>
        get() = _resultAll

    private var _resultRing = MutableLiveData<MutableList<Shop>>()
    val resultRing: LiveData<MutableList<Shop>>
        get() = _resultRing

    private var _resultBadge = MutableLiveData<MutableList<Shop>>()
    val resultBadge: LiveData<MutableList<Shop>>
        get() = _resultBadge

    private var _resultCover = MutableLiveData<MutableList<Shop>>()
    val resultCover: LiveData<MutableList<Shop>>
        get() = _resultCover

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
                _myInventoryItems.value = shopService.requestMyInventory(userEmail)

                _resultAll.value = mutableListOf()
                _myInventoryItems.value?.lingList?.let { _resultAll.value?.addAll(it) }
                _myInventoryItems.value?.badgeList?.let { _resultAll.value?.addAll(it) }
                _myInventoryItems.value?.backgroundList?.let { _resultAll.value?.addAll(it) }

                _resultRing.value = _myInventoryItems.value?.lingList
                _resultBadge.value = _myInventoryItems.value?.badgeList
                _resultCover.value = _myInventoryItems.value?.backgroundList

                Log.d(TAG, "requestMyInventory: ${_resultAll.value}")
            } catch (e: ConnectException) {
                _myInventoryItems.value = InventoryResult()
                mainViewModel.setConnectException()
            } catch (e: Exception) {
                _myInventoryItems.value = InventoryResult()
                mainViewModel.setConnectException()
            }
        }
    }

    /**
     * 아이템 장착 요청
     */
    fun requestItemEquipment(item: Shop, mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                val userEmail = ApplicationClass.sharedPreferences.getString("userEmail")!!
                // 반환 shop 저장
                shopService.requestItemEquipment(
                    Equipment(
                        userEmail,
                        item.itemId,
                        item.itemType,
                    ),
                )
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            } catch (e: Exception) {
                mainViewModel.setConnectException()
            }
        }
    }
}
