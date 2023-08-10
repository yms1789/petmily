package com.petmily.presentation.view.dialog

import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.databinding.DialogBoardOptionBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.BoardViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Comment
import com.petmily.repository.dto.User

class OptionDialog(
    private val mainActivity: MainActivity,
    private val mainViewModel: MainViewModel,
    private val boardViewModel: BoardViewModel,
) : Dialog(mainActivity) {
    private val binding: DialogBoardOptionBinding by lazy {
        DialogBoardOptionBinding.inflate(layoutInflater)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    
        // Dialog 자체 window가 wrap_content이므로 match_parent로 변경
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    
    fun showBoardOptionDialog() = with(binding) {
        tvTitle.text = mainActivity.getString(R.string.boardoption_dialog_title)
        btnLeft.text = mainActivity.getString(R.string.boardoption_dialog_update)
        btnRight.text = mainActivity.getString(R.string.boardoption_dialog_delete)
    
        btnLeft.setOnClickListener {
            dismiss()
            mainActivity.changeFragment("feed add")
        }
        btnRight.setOnClickListener {
            boardViewModel.deleteBoard(
                boardViewModel.selectedBoard.boardId,
                User(userEmail = ApplicationClass.sharedPreferences.getString("userEmail") ?: ""),
                mainViewModel,
            )
            dismiss()
        }
        
        show()
    }

    fun showCommentOptionDialog() = with(binding) {
        tvTitle.text = mainActivity.getString(R.string.commentoption_dialog_title)
        btnLeft.text = mainActivity.getString(R.string.commentoption_dialog_tag)
        btnRight.text = mainActivity.getString(R.string.commentoption_dialog_delete)
    
        btnLeft.setOnClickListener {
//            initCommentDialogForReply(boardViewModel.selectedComment)
            optionDialogClickListener.commentTagClick()
            dismiss()
        }
        btnRight.setOnClickListener {
            boardViewModel.deleteComment(boardViewModel.selectedComment.commentId, mainViewModel)
            dismiss()
        }
        
        show()
    }

    interface OptionDialogClickListener {
        fun commentTagClick()
    }
    private lateinit var optionDialogClickListener: OptionDialogClickListener
    fun setOptionDialogClickListener(optionDialogClickListener: OptionDialogClickListener) {
        this.optionDialogClickListener = optionDialogClickListener
    }
}
