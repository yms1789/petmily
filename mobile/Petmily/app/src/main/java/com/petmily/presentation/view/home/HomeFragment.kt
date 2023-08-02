package com.petmily.presentation.view.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.DialogCommentBinding
import com.petmily.databinding.FragmentHomeBinding
import com.petmily.databinding.ItemBoardBinding
import com.petmily.databinding.ItemCommentBinding
import com.petmily.databinding.ItemHomeCurationBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.repository.dto.Board
import com.petmily.repository.dto.Comment
import com.petmily.repository.dto.Curation
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

private const val TAG = "Fetmily_HomeFragment"
private const val CURATION_JOB_DELAY = 3000L
class HomeFragment :
    BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private lateinit var mainActivity: MainActivity

    private lateinit var homeCurationAdapter: HomeCurationAdapter
    private lateinit var boardAdapter: BoardAdapter
    private lateinit var commentAdapter: CommentAdapter

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

    // 큐레이션 데이터 TODO: api 통신 후 적용되는 실제 데이터로 변경
    private val curations =
        listOf(
            Curation(),
            Curation(),
            Curation(),
            Curation(),
            Curation(),
        )

    // 피드 게시물 데이터 TODO: api 통신 후 적용되는 실제 데이터로 변경
    private val boards =
        listOf(
            Board(), Board(), Board(), Board(), Board(),
            Board(), Board(), Board(), Board(), Board(),
            Board(), Board(), Board(), Board(), Board(),
        )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCommentAdapter()
        initCurations()
        initBoards()
        initViewPager()
        initDialog()
    }

    override fun onResume() {
        super.onResume()
        curationJobCreate()
    }

    override fun onPause() {
        super.onPause()
        curationJob.cancel()
    }

    private fun initAdapter() = with(binding) {
        // 클릭 이벤트 처리
        homeCurationAdapter = HomeCurationAdapter().apply {
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
        boardAdapter = BoardAdapter().apply {
            setBoardClickListener(object : BoardAdapter.BoardClickListener {
                override fun likeClick(compoundButton: CompoundButton, binding: ItemBoardBinding, board: Board, position: Int) {
//                    compoundButton.startAnimation(likeAnimation)
                }

                override fun commentClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    showCommentDialog(board)
                }

                override fun bookmarkClick(compoundButton: CompoundButton, binding: ItemBoardBinding, board: Board, position: Int) {
//                    compoundButton.startAnimation(bookmarkAnimation)
                }

                override fun profileClick(binding: ItemBoardBinding, board: Board, position: Int) {
                    // TODO: 클릭 이벤트 처리, 프로필 이미지 혹은 이름 클릭 시 해당 유저 프로필로 이동
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
        homeCurationAdapter.setCurations(curations)
    }

    // 피드 게시물 데이터 초기화 TODO: api 통신 코드로 변경
    private fun initBoards() {
        boardAdapter.setBoards(boards)
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

                ciCuration.animatePageSelected((vpCuration.currentItem + curations.size - 1) % curations.size)
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
        ciCuration.createIndicators(curations.size, 0)
    }
    
    private fun initDialog() = with(commentDialogBinding) {
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

    // 일정 시간마다 자동으로 큐레이션 이동
    private fun curationJobCreate() {
        curationJob = lifecycleScope.launchWhenResumed {
            delay(CURATION_JOB_DELAY)
            binding.vpCuration.setCurrentItem(binding.vpCuration.currentItem % curations.size + 1, true)
        }
    }

    // 댓글 버튼 클릭 시 댓글 Dialog 열기
    private fun showCommentDialog(board: Board) {
        // TODO: Dialog에 데이터 삽입
        commentDialog.show()
    }
}
