package com.petmily.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petmily.repository.api.board.BoardService
import com.petmily.repository.dto.Board
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.net.ConnectException

private const val TAG = "Fetmily_BoardViewModel"
class BoardViewModel : ViewModel() {
    private val boardService by lazy { BoardService() }
    
    // 피드 등록 통신 결과
    private val _isBoardSaved = MutableLiveData<Boolean>()
    val isBoardSaved: LiveData<Boolean>
        get() = _isBoardSaved
    
    // 피드 수정 통신 결과
    private val _isBoardUpdated = MutableLiveData<Boolean>()
    val isBoardUpdated: LiveData<Boolean>
        get() = _isBoardUpdated
    
    /**
     * 피드 등록 통신
     */
    fun saveBoard(file: List<MultipartBody.Part>?, board: Board, mainViewModel: MainViewModel) {
        Log.d(TAG, "saveBoard: 피드 등록")
        viewModelScope.launch {
            try {
                _isBoardSaved.value = boardService.boardSave(file, board)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
    
    /**
     * 피드 수정 통신
     */
    fun updateBoard(boardId: Long, file: List<MultipartBody.Part>?, board: Board, mainViewModel: MainViewModel) {
        Log.d(TAG, "updateBoard: 피드 수정")
        viewModelScope.launch {
            try {
                _isBoardUpdated.value = boardService.boardUpdate(boardId, file, board)
            } catch (e: ConnectException) {
                mainViewModel.setConnectException()
            }
        }
    }
}
