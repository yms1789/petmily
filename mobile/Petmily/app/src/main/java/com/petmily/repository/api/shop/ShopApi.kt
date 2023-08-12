package com.petmily.repository.api.shop

import com.petmily.repository.dto.Equipment
import com.petmily.repository.dto.InventoryResult
import com.petmily.repository.dto.RequestItem
import com.petmily.repository.dto.Shop
import retrofit2.http.*

interface ShopApi {

    /**
     * API - 아이템 뽑기 요청
     */
    @POST("/item/getRandom")
    suspend fun requestItem(
        @Body body: RequestItem,
    ): Shop

    /**
     * API - 내 인벤토리 데이터 요청
     */
    @GET("/item/inventory")
    suspend fun requestMyInventory(
        @Query("userEmail") userEmail: String,
    ): InventoryResult

    /**
     * 아이템 장착 요청
     */
    @PUT("/item/equipment")
    suspend fun requestItemEquipment(@Body body: Equipment)
}
