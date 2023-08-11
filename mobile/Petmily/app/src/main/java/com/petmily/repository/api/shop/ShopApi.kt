package com.petmily.repository.api.shop

import com.petmily.repository.dto.RequestItem
import com.petmily.repository.dto.Shop
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    ): MutableList<Shop>
}
