package com.petmily.repository.api.curation

import android.util.Log
import com.petmily.repository.dto.CurationResult
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Petmily_CurationService"

class CurationService {
    suspend fun requestCurationData(species: String): CurationResult {
        return try {
            return RetrofitUtil.curationApi.requestCurationData(species)
        } catch (e: ConnectException) {
            Log.d(TAG, "request curation: ${e.message}")
            throw ConnectException()
        }
    }
}
