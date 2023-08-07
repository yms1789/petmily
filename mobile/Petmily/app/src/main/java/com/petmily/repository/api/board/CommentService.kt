package com.petmily.repository.api.board

import android.util.Log
import com.petmily.repository.dto.Comment
import com.petmily.util.RetrofitUtil
import java.net.ConnectException

private const val TAG = "Fetmily_CommentService"
class CommentService {
    /**
     * 댓글 등록
     */
    suspend fun commentSave(comment: Comment): Comment {
        return try {
            RetrofitUtil.commentApi.commentSave(comment)
        } catch (e: ConnectException) {
            Log.d(TAG, "commentSave: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "commentSave: ${e.message}")
            Comment()
        }
    }
    
    /**
     * 댓글 삭제
     */
    suspend fun commentDelete(commentId: Long): Boolean {
        return try {
            RetrofitUtil.commentApi.commentDelete(commentId)
            true
        } catch (e: ConnectException) {
            Log.d(TAG, "commentDelete: ${e.message}")
            throw ConnectException()
        } catch (e: Exception) {
            Log.d(TAG, "commentDelete: ${e.message}")
            false
        }
    }
}
