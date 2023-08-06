package com.petmily.repository.api.curation

import android.util.Log
import com.petmily.presentation.view.info.user.UserInfoInputFragment.Companion.species
import com.petmily.repository.dto.CurationBookmark
import com.petmily.repository.dto.CurationResult
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Petmily_CurationService"

class CurationService {

    /**
     * Curation data 요청
     */
    suspend fun requestCurationData(species: String): CurationResult {
        return try {
            return RetrofitUtil.curationApi.requestCurationData(species)
        } catch (e: ConnectException) {
            Log.d(TAG, "request curation: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            CurationResult()
        }
    }

    /**
     * Curation bookmark 요청
     */
    suspend fun requestCurationBookmark(curationBookmark: CurationBookmark): Boolean {
        return try {
            RetrofitUtil.curationApi.requestCurationBookmark(curationBookmark)
            return true
        } catch (e: ConnectException) {
            Log.d(TAG, "request curation: ${e.message}")
            throw ConnectException()
        }
    }
}
