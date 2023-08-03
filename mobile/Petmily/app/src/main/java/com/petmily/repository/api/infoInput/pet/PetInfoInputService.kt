package com.petmily.repository.api.infoInput.pet

import android.util.Log
import com.petmily.repository.dto.Pet
import com.petmily.util.RetrofitUtil
import okhttp3.MultipartBody
import java.lang.Exception
import java.net.ConnectException

private const val TAG = "Petmily_PetInfoInput"
class PetInfoInputService {
    suspend fun petSave(file: MultipartBody.Part, pet: Pet): Boolean {
        return try {
            RetrofitUtil.petInfoInputApi.petSave(file, pet)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "petSave: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "petSave: ${e.message}")
            false
        }
    }
}
