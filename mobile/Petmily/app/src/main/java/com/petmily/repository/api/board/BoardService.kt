package com.petmily.repository.api.board

import android.util.Log
import com.petmily.repository.dto.Board
import com.petmily.util.RetrofitUtil
import okhttp3.MultipartBody
import java.net.ConnectException

private const val TAG = "Fetmily_BoardService"
class BoardService {
    /**
     * 피드 등록
     */
    suspend fun boardSave(file: List<MultipartBody.Part>?, board: Board): Boolean {
        return try {
            RetrofitUtil.boardApi.boardSave(file, board)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "boardSave: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "boardSave: ${e.message}")
            false
        }
    }
    
    /**
     * 피드 수정
     */
    suspend fun boardUpdate(boardId: Long, file: List<MultipartBody.Part>?, board: Board): Boolean {
        return try {
            RetrofitUtil.boardApi.boardUpdate(boardId, file, board)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "boardUpdate: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "boardUpdate: ${e.message}")
            false
        }
    }
}
