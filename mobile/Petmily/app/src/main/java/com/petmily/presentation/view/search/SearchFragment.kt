package com.petmily.presentation.view.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentSearchBinding
import com.petmily.databinding.ItemBoardBinding
import com.petmily.databinding.ItemSearchUserBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.view.dialog.CommentDialog
import com.petmily.presentation.view.dialog.OptionDialog
import com.petmily.presentation.view.home.BoardAdapter
import com.petmily.presentation.viewmodel.BoardViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Board
import com.petmily.repository.dto.User

private const val TAG = "Fetmily_SearchFragment"
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }

    private lateinit var userAdapter: SearchUserAdapter
    private lateinit var boardAdapter: BoardAdapter
    private lateinit var curationAdapter: SearchCurationAdapter
    
    private val mainViewModel: MainViewModel by activityViewModels()
    private val boardViewModel: BoardViewModel by activityViewModels()
    
    private val commentDialog by lazy { CommentDialog(mainActivity, mainViewModel, boardViewModel) }
    private val optionDialog by lazy { OptionDialog(mainActivity, mainViewModel, boardViewModel) }
    
    // 피드 게시물 데이터 TODO: api 통신 후 적용되는 실제 데이터로 변경
    private val boards =
        listOf(
            Board(), Board(), Board(), Board(), Board(),
            Board(), Board(), Board(), Board(), Board(),
            Board(), Board(), Board(), Board(), Board(),
        )
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initBtn()
        initAdapter()
    }
    
    private fun initBtn() = with(binding) {
        // 검색 돋보기 버튼 클릭
        ivSearch.setOnClickListener {
        }
        
        // 사용자 카테고리 버튼 클릭
        btnCategoryUser.setOnClickListener {
            rcvSearch.adapter = userAdapter
        }
        
        // 피드 카테고리 버튼 클릭
        btnCategoryBoard.setOnClickListener {
            boardAdapter.setBoards(boards)
            rcvSearch.adapter = boardAdapter
        }

        // 큐레이션 카테고리 버튼 클릭
        btnCategoryCuration.setOnClickListener {
            rcvSearch.adapter = curationAdapter
        }

        // 뒤로가기 버튼 클릭
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun initAdapter() = with(binding) {
        // 사용자 adapter
        userAdapter = SearchUserAdapter().apply {
            setUserClickListener(object : SearchUserAdapter.UserClickListener {
                override fun userClick(binding: ItemSearchUserBinding, user: User, position: Int) {
                    // TODO("Not yet implemented")
                }
            })
        }
        rcvSearch.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(mainActivity, LinearLayoutManager.VERTICAL))
        }

        // 피드 adapter
        boardAdapter = BoardAdapter(mainActivity).apply {
            setBoardClickListener(object : BoardAdapter.BoardClickListener {
                override fun heartClick(
                    isClicked: Boolean,
                    binding: ItemBoardBinding,
                    board: Board,
                    position: Int,
                ) {
                    if (isClicked) {
                        // 좋아요 등록
                        boardViewModel.registerHeart(
                            board.boardId,
                            ApplicationClass.sharedPreferences.getString("userEmail") ?: "",
                        )
                    } else {
                        // 좋아요 취소
                        boardViewModel.deleteHeart(
                            board.boardId,
                            ApplicationClass.sharedPreferences.getString("userEmail") ?: ""
                        )
                    }
                }
                override fun commentClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    commentDialog.showCommentDialog(board)
                }
                override fun profileClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    // TODO("Not yet implemented")
                }
                override fun optionClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    optionDialog.showBoardOptionDialog()
                }
            })
        }
        
        // 큐레이션 adapter
        curationAdapter = SearchCurationAdapter().apply {
            // TODO("Not yet implemented")
        }
    }
    
    private fun initObserver() = with(boardViewModel) {
        // 전체 피드 조회
        selectedBoardList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                // 피드 전체 조회 실패
                Log.d(TAG, "initObserver: 피드 전체 조회 실패")
            } else {
                // 피드 전체 조회 성공
                boardAdapter.setBoards(it)
            }
        }
        
        // 내 피드 삭제
        initIsBoardDeleted()
        isBoardDeleted.observe(viewLifecycleOwner) {
            if (!it) {
                // 피드 삭제 실패
                mainActivity.showSnackbar("게시물 삭제에 실패하였습니다.")
            } else {
                // 피드 삭제 성공
                mainActivity.showSnackbar("게시물이 삭제되었습니다.")
            }
        }
        
        // 댓글 등록
        initCommentSaveResult()
        commentSaveResult.observe(viewLifecycleOwner) {
            if (it.commentId == 0L) {
                // 댓글 등록 실패
                mainActivity.showSnackbar("댓글 등록에 실패하였습니다.")
            } else {
//                commentDialogBinding.etComment.text!!.clear()
                commentDialog.clearEditText()
            }
        }
        
        // 댓글 삭제
        initIsCommentDeleted()
        isCommentDeleted.observe(viewLifecycleOwner) {
            if (!it) {
                // 댓글 삭제 실패
                mainActivity.showSnackbar("댓글 삭제에 실패하였습니다.")
            } else {
                // 댓글 삭제 성공
            }
            optionDialog.dismiss()
        }
    }
}
