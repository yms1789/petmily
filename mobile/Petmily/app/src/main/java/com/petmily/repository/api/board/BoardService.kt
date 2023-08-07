package com.petmily.repository.api.board

import android.util.Log
import com.petmily.repository.dto.Board
import com.petmily.repository.dto.HashTagRequestDto
import com.petmily.util.RetrofitUtil
import okhttp3.MultipartBody
import java.net.ConnectException

private const val TAG = "Fetmily_BoardService"
class BoardService {
    /**
     * 피드 등록
     */
    suspend fun boardSave(file: List<MultipartBody.Part>?, board: Board, hashTagRequestDto: HashTagRequestDto): Boolean {
        return try {
            RetrofitUtil.boardApi.boardSave(file, board, hashTagRequestDto)
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
    suspend fun boardUpdate(boardId: Long, file: List<MultipartBody.Part>?, board: Board, hashTagRequestDto: HashTagRequestDto): Boolean {
        return try {
            RetrofitUtil.boardApi.boardUpdate(boardId, file, board, hashTagRequestDto)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "boardUpdate: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "boardUpdate: ${e.message}")
            false
        }
    }
    
    /**
     * 피드 삭제
     */
    suspend fun boardDelete(boardId: Long): Boolean {
        return try {
            RetrofitUtil.boardApi.boardDelete(boardId)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "boardDelete: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "boardDelete: ${e.message}")
            false
        }
    }
    
    /**
     * 피드 전체 조회
     */
    suspend fun boardSelectAll(userEmail: String): List<Board> {
        return try {
            RetrofitUtil.boardApi.boardSelectAll(userEmail)
        } catch (e: ConnectException) {
            Log.d(TAG, "boardSelectAll: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "boardSelectAll: ${e.message}")
            listOf()
        }
    }
    
    /**
     * 피드 단일 조회
     */
    suspend fun boardSelectOne(boardId: Long): Board {
        return try {
            RetrofitUtil.boardApi.boardSelectOne(boardId)
        } catch (e: ConnectException) {
            Log.d(TAG, "boardSelect: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "boardSelect: ${e.message}")
            Board()
        }
    }
    
    /**
     * 좋아요 등록
     */
    suspend fun registerHeart(board: Board): Boolean {
        return try {
            RetrofitUtil.boardApi.registerHeart(board)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "registerHeart: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "registerHeart: ${e.message}")
            false
        }
    }
    
    /**
     * 좋아요 취소
     */
    suspend fun deleteHeart(board: Board): Boolean {
        return try {
            RetrofitUtil.boardApi.deleteHeart(board)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "deleteHeart: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "deleteHeart: ${e.message}")
            false
        }
    }
}
