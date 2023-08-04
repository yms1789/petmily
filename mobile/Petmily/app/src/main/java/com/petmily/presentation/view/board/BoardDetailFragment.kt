package com.petmily.presentation.view.board

import android.app.Dialog
import android.os.Bundle
import android.system.Os.bind
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.DialogCommentBinding
import com.petmily.databinding.FragmentBoardDetailBinding
import com.petmily.databinding.ItemCommentBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.view.home.BoardImgAdapter
import com.petmily.presentation.view.home.CommentAdapter
import com.petmily.repository.dto.Comment

class BoardDetailFragment :
    BaseFragment<FragmentBoardDetailBinding>(FragmentBoardDetailBinding::bind, R.layout.fragment_board_detail) {
    
    private val mainActivity: MainActivity by lazy {
        context as MainActivity
    }
    
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var boardImgAdapter: BoardImgAdapter
    
    // 임시 이미지 데이터 TODO: api 통신 후 적용되는 실제 데이터로 변경
    private val imgs = listOf(
        "",
        "",
        "",
        "",
        "",
    )
    
    // 댓글 BottomSheetDialog
    private val commentDialog: Dialog by lazy {
        BottomSheetDialog(mainActivity).apply {
            setContentView(R.layout.dialog_comment)
        }
    }
    private val commentDialogBinding: DialogCommentBinding by lazy {
        DialogCommentBinding.bind(commentDialog.findViewById(R.id.cl_dialog_comment))
    }
    
    // 좋아요, 북마크 애니메이션
    val likeAnimation by lazy {
        ScaleAnimation(
            0.7f,
            1.0f,
            0.7f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
        ).apply {
            duration = 500
            interpolator = BounceInterpolator()
        }
    }
    val bookmarkAnimation by lazy {
        ScaleAnimation(
            0.7f,
            1.0f,
            0.7f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
        ).apply {
            duration = 500
            interpolator = BounceInterpolator()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBtn()
        initCommentAdapter()
        initDialog()
        initImgViewPager()
    }
    
    private fun initBtn() = with(binding) {
        // 뒤로가기 버튼 클릭
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        
        // 좋아요
        btnLike.setOnCheckedChangeListener { compoundButton, _ ->
            compoundButton.startAnimation(likeAnimation)
        }
        
        // 댓글
        ivComment.setOnClickListener {
            showCommentDialog()
        }
        
        // 북마크
        btnBookmark.setOnCheckedChangeListener { compoundButton, _ ->
            compoundButton.startAnimation(bookmarkAnimation)
        }
        
        // 프로필 클릭
        ivProfile.setOnClickListener {
        }
    }
    
    private fun initCommentAdapter() = with(commentDialogBinding) {
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
        
        rcvComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        }
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
    
    private fun initImgViewPager() = with(binding) {
        boardImgAdapter = BoardImgAdapter(mainActivity).apply {
            setImgs(imgs)
        }
        vpBoardImg.adapter = boardImgAdapter
        
        // 이미지 순서에 따른 하단 점 설정, img 데이터 설정 이후에 설정해야 오류 없음
        ciBoardImg.setViewPager(vpBoardImg)
    }
    
    /**
     * 댓글 버튼 클릭 시 댓글 Dialog 열기
     */
    private fun showCommentDialog() {
        // TODO: Dialog에 데이터 삽입
        commentDialog.show()
    }
    
}
