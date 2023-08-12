package com.petmily.repository.api.shop

import android.util.Log
import com.petmily.repository.dto.Equipment
import com.petmily.repository.dto.InventoryResult
import com.petmily.repository.dto.RequestItem
import com.petmily.repository.dto.Shop
import com.petmily.util.RetrofitUtil
import java.lang.Exception
import java.net.ConnectException

private const val TAG = "Petmily_ShopService"
class ShopService {
    /**
     * API - 아이템 뽑기 요청
     */
    suspend fun requestItem(requestItem: RequestItem): Shop {
        return try {
            Log.d(TAG, "requestItem post: $requestItem")
            val result = RetrofitUtil.shopApi.requestItem(requestItem)
            Log.d(TAG, "requestItem Result: ${result.itemName}")
            return result
        } catch (e: ConnectException) {
            throw ConnectException()
        } catch (e: Exception) {
            throw Exception()
        }
    }

    /**
     * API - 내 인벤토리 데이터 요청
     */
    suspend fun requestMyInventory(userEmail: String): InventoryResult {
        return try {
            val result = RetrofitUtil.shopApi.requestMyInventory(userEmail)
            Log.d(TAG, "requestItem: $result")
            return result
        } catch (e: ConnectException) {
            throw ConnectException()
        } catch (e: Exception) {
            throw Exception()
        }
    }

    /**
     * 아이템 장착 요청
     */
    suspend fun requestItemEquipment(equipment: Equipment) {
        return try {
            RetrofitUtil.shopApi.requestItemEquipment(equipment)
        } catch (e: ConnectException) {
            throw ConnectException()
        } catch (e: Exception) {
            throw Exception()
        }
    }
}
