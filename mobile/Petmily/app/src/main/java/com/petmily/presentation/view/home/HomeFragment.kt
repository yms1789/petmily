package com.petmily.presentation.view.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.DialogBoardOptionBinding
import com.petmily.databinding.DialogCommentBinding
import com.petmily.databinding.FragmentHomeBinding
import com.petmily.databinding.ItemBoardBinding
import com.petmily.databinding.ItemCommentBinding
import com.petmily.databinding.ItemHomeCurationBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.BoardViewModel
import com.petmily.presentation.viewmodel.CurationViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Board
import com.petmily.repository.dto.Comment
import com.petmily.repository.dto.Curation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

private const val TAG = "petmily_HomeFragment"
private const val CURATION_JOB_DELAY = 3000L
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity

    private lateinit var homeCurationAdapter: HomeCurationAdapter
    private lateinit var boardAdapter: BoardAdapter
    private lateinit var commentAdapter: CommentAdapter

    private val mainViewModel: MainViewModel by activityViewModels()
    private val boardViewModel: BoardViewModel by activityViewModels()
    private val curationViewModel: CurationViewModel by activityViewModels()

    // curation ViewPager 자동 스크롤 job
    private lateinit var curationJob: Job

    // 댓글 BottomSheetDialog
    private val commentDialog: Dialog by lazy {
        BottomSheetDialog(mainActivity).apply {
            setContentView(R.layout.dialog_comment)
        }
    }
    private val commentDialogBinding: DialogCommentBinding by lazy {
        DialogCommentBinding.bind(commentDialog.findViewById(R.id.cl_dialog_comment))
    }
    
    // 3점(옵션) BottomSheetDialog
    private val optionDialog: Dialog by lazy {
        BottomSheetDialog(mainActivity).apply {
            setContentView(R.layout.dialog_board_option)
        }
    }
    private val optionDialogBinding: DialogBoardOptionBinding by lazy {
        DialogBoardOptionBinding.bind(optionDialog.findViewById(R.id.cl_dialog_board_option))
    }
    
    // 3점(옵션) BottomSheetDialog
//    private val optionDialog: Dialog by lazy {
//        BottomSheetDialog(mainActivity).apply {
//            setContentView(R.layout.)
//        }
//    }
//    private val optionDialogBinding:  by lazy {
//
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
//        curationViewModel.getRandomCurationList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCommentAdapter()
//        initCurations()
        initBoards()
        initViewPager()
        initCommentDialog()
        initOptionDialog()
        initBtn()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        if (curationViewModel.randomCurationList.value!!.isNotEmpty()) curationJobCreate()
    }

    override fun onPause() {
        super.onPause()
        if (this::curationJob.isInitialized) curationJob.cancel()
    }

    private fun initAdapter() = with(binding) {
        // 클릭 이벤트 처리
        homeCurationAdapter = HomeCurationAdapter(curationViewModel).apply {
            setCurationClickListener(object : HomeCurationAdapter.CurationClickListener {
                override fun curationClick(
                    binding: ItemHomeCurationBinding,
                    curation: Curation,
                    position: Int,
                ) {
                    // TODO: 클릭 이벤트 처리, 큐레이션 클릭 시 해당 큐레이션 화면으로 이동
                }
            })
        }
        boardAdapter = BoardAdapter(mainActivity).apply {
            setBoardClickListener(object : BoardAdapter.BoardClickListener {
                override fun likeClick(compoundButton: CompoundButton, binding: ItemBoardBinding, board: Board, position: Int) {
                }

                override fun commentClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    showCommentDialog(board)
                }

                override fun profileClick(binding: ItemBoardBinding, board: Board, position: Int) {
                }
    
                override fun optionClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    boardViewModel.selectedBoard = board
                    optionDialog.show()
                }
            })
        }
        commentAdapter = CommentAdapter(mainActivity).apply {
            setCommentClickListener(object : CommentAdapter.CommentClickListener {
                override fun commentClick(
                    binding: ItemCommentBinding,
                    comment: Comment,
                    position: Int,
                ) {
                    // TODO: 클릭 이벤트 처리, 답글 더보기 클릭하면 답글 열림
                }
            })
        }

        vpCuration.adapter = homeCurationAdapter
        rcvBoard.apply {
            adapter = boardAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun initCommentAdapter() = with(commentDialogBinding) {
        rcvComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    // 큐레이션 데이터 초기화 TODO: api 통신 코드로 변경
    private fun initCurations() {
        if (curationViewModel.randomCurationList.value!!.isNotEmpty()) {
            homeCurationAdapter.setCurations(curationViewModel.randomCurationList.value)
        }
    }

    // 피드 게시물 데이터 초기화 TODO: api 통신 코드로 변경
    private fun initBoards() {
        boardViewModel.selectAllBoard(ApplicationClass.sharedPreferences.getString("userEmail") ?: "", mainViewModel)
//        boardAdapter.setBoards(boards)
    }

    // 댓글 데이터 초기화 TODO: 클릭된 피드에 따라 댓글 데이터 변경
    private fun initComments(comments: List<Comment>) {
        commentAdapter.setComments(comments)
    }

    // ViewPager 초기 설정
    private fun initViewPager() = with(binding) {
        // 무한 스크롤을 위해 HomeCurationAdapter 내 리스트의 맨앞, 맨뒤에 반대 방향 값 추가하여 실제 데이터는 1부터 시작
        vpCuration.setCurrentItem(1, false)
        vpCuration.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                ciCuration.animatePageSelected((vpCuration.currentItem + curationViewModel.randomCurationList.value!!.size - 1) % curationViewModel.randomCurationList.value!!.size)
                when (vpCuration.currentItem) {
                    homeCurationAdapter.itemCount - 1 -> vpCuration.setCurrentItem(1, false)
                    0 -> vpCuration.setCurrentItem(homeCurationAdapter.itemCount - 2, false)
                }

                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        if (!curationJob.isActive) curationJobCreate()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> curationJob.cancel()
                }
            }
        })

        // ViewPager 하단 위치 표시 점
        ciCuration.createIndicators(curationViewModel.randomCurationList.value!!.size, 0)
    }

    private fun initCommentDialog() = with(commentDialogBinding) {
        etComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (etComment.length() > 0 && tvCommentUpload.visibility == View.GONE) {
                    tvCommentUpload.visibility = View.VISIBLE
                } else if (etComment.length() == 0) {
                    tvCommentUpload.visibility = View.GONE
                }
            }
        })
    }
    
    private fun initOptionDialog() = with(optionDialogBinding) {
        btnUpdateBoard.setOnClickListener {
            mainActivity.changeFragment("feed add")
        }
        btnDeleteBoard.setOnClickListener {
            boardViewModel.deleteBoard(boardViewModel.selectedBoard.boardId, mainViewModel)
        }
    }

    private fun initBtn() = with(binding) {
        ivSearch.setOnClickListener {
            mainActivity.changeFragment("search")
        }

        ivNoti.setOnClickListener {
            mainActivity.changeFragment("notification")
        }
    }

    private fun initObserver() {
        // 전체 피드 조회
        boardViewModel.selectedBoardList.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                // 피드 전체 조회 실패
                Log.d(TAG, "initObserver: 피드 전체 조회 실패")
            } else {
                // 피드 전체 조회 성공
                boardAdapter.setBoards(it)
            }
        }

        // 랜덤 큐레이션 List
        curationViewModel.randomCurationList.observe(viewLifecycleOwner) {
            Log.d(TAG, "getRandomCurationList Observer: ${it.size}")
    
            if (it.isNotEmpty()) {
                homeCurationAdapter.setCurations(it)
            }
        }
        
        // 내 피드 삭제
        boardViewModel.initIsBoardDeleted()
        boardViewModel.isBoardDeleted.observe(viewLifecycleOwner) {
            if (!it) {
                // 피드 삭제 실패
                mainActivity.showSnackbar("게시물 삭제에 실패하였습니다.")
            } else {
                // 피드 삭제 성공
                mainActivity.showSnackbar("게시물이 삭제되었습니다.")
            }
        }
    }

    // 일정 시간마다 자동으로 큐레이션 이동
    private fun curationJobCreate() {
        curationJob = lifecycleScope.launchWhenResumed {
            delay(CURATION_JOB_DELAY)
            binding.vpCuration.setCurrentItem(binding.vpCuration.currentItem % curationViewModel.randomCurationList.value!!.size + 1, true)
//            binding.vpCuration.setCurrentItem((binding.vpCuration.currentItem + 1) % curationViewModel.randomCurationList.value!!.size, true)
        }
    }

    // 댓글 버튼 클릭 시 댓글 Dialog 열기
    private fun showCommentDialog(board: Board) = with(commentDialogBinding) {
        Glide.with(commentDialogBinding.root)
            .load(board.userProfileImageUrl)
            .into(ivProfile)
        
        tvName.text = board.userNickname
        tvCommentContent.text = board.boardContent
        tvLikeCnt.text = board.heartCount.toString()
        btnLike.isChecked = board.likedByCurrentUser
        
        commentAdapter.setComments(board.comments)
        
        commentDialog.show()
    }
}
