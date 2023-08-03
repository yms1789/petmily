package com.petmily.repository.api.infoInput.pet

import com.petmily.repository.dto.Pet
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PetInfoInputApi {
    @Multipart
    @POST("/pet/save")
    suspend fun petSave(
        @Part file: MultipartBody.Part,
        @Part body: Pet,
    )
}
