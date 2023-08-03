package com.petmily.repository.api.board

import com.petmily.repository.dto.Board
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface BoardApi {
    @Multipart
    @POST("/board/save")
    suspend fun boardSave(
        @Part file: List<MultipartBody.Part>?,
        @Part("boardRequestDto") boardRequestDto: Board,
    )
    
    @Multipart
    @PUT("/board/{boardId}")
    suspend fun boardUpdate(
        @Path("boardId") boardId: Long,
        @Part file: List<MultipartBody.Part>?,
        @Part("boardRequestDto") boardRequestDto: Board,
    )
}
