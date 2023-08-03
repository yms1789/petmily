package com.petmily.presentation.view.board

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.system.Os.bind
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide.init
import com.google.android.material.chip.Chip
import com.petmily.R
import com.petmily.config.ApplicationClass
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentBoardWriteBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.BoardViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Board
import com.petmily.util.CheckPermission
import com.petmily.util.GalleryUtil
import com.petmily.util.UploadUtil
import okhttp3.MultipartBody
import java.io.File

private const val TAG = "Fetmily_BoardWriteFragment"
class BoardWriteFragment :
    BaseFragment<FragmentBoardWriteBinding>(FragmentBoardWriteBinding::bind, R.layout.fragment_board_write) {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by activityViewModels()
    private val boardViewModel: BoardViewModel by activityViewModels()

    private lateinit var galleryUtil: GalleryUtil
    private val uploadUtil by lazy { UploadUtil() }
    private lateinit var checkPermission: CheckPermission

    // image Adapter
    private lateinit var imageAdapter: ImageAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        galleryUtil = GalleryUtil()
        checkPermission = CheckPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initAdapter()
        initButton()
        initEditText()
        initImageView()
        initObserver()
    }

    private fun init() = with(binding) {
        mainViewModel.setFromGalleryFragment("addFeedFragment")

        // 글 작성 부분을 최초 포커스로 지정
        etBoardContent.requestFocus()
    }

    private fun initAdapter() = with(binding) {
        imageAdapter = ImageAdapter()

        rcvSelectImage.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initButton() = with(binding) {
        // 등록 버튼
        btnAddBoard.setOnClickListener {
            if (isValidInput() && mainActivity.isNetworkConnected()) {
//                val files: List<MultipartBody.Part>? = null
                val files: ArrayList<MultipartBody.Part> = arrayListOf()
                mainViewModel.addPhotoList.value!!.forEach {
//                    Log.d(TAG, "initButton: $it")
                    files.add(uploadUtil.createMultipartFromUri(mainActivity, File(it.imgUrl)))
                }
                val board = Board(
                    boardContent = etBoardContent.text.toString(),
                    userEmail = ApplicationClass.sharedPreferences.getString("userEmail") ?: "",
                )
                boardViewModel.saveBoard(files, board, mainViewModel)
            }
        }
    }

    private fun initImageView() = with(binding) {
        // 이미지 추가 버튼 (N장)
        ivSelectImage.setOnClickListener {
            if (checkPermission.requestStoragePermission()) { // 갤러리 접근 권한 체크
                if (galleryUtil.getImages(mainActivity, mainViewModel)) { // 갤러리 이미지를 모두 로드 했다면
                    mainActivity.changeFragment("gallery")
                }
            }
        }
    }

    private fun initEditText() = with(binding) {
        // 태그
        etPetName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addChipsFromEditText()
                true
            } else {
                false
            }
        }
    }
    
    private fun initObserver() = with(boardViewModel) {
        // 게시물 등록 결과
        isBoardSaved.observe(viewLifecycleOwner) {
            if (!it) {
                // 게시물 등록 실패
                mainActivity.showSnackbar("게시물 등록에 실패하였습니다.")
            } else {
                // 게시물 등록 성공
                mainActivity.showSnackbar("게시물이 등록되었습니다.")
                parentFragmentManager.popBackStack()
            }
        }
        
        // 게시물 수정 결과
        isBoardUpdated.observe(viewLifecycleOwner) {
            if (!it) {
                // 게시물 수정 실패
                mainActivity.showSnackbar("게시물 수정에 실패하였습니다.")
            } else {
                // 게시물 수정 성공
                mainActivity.showSnackbar("게시물이 수정되었습니다.")
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun addChipsFromEditText() = with(binding) {
        val newText = etPetName.text.toString().trim()
        if (newText.isNotBlank()) {
            val tags = newText.split("#").filter { it.isNotBlank() }
            for (tag in tags) {
                val chip = createChip(tag)
                chipGroup.addView(chip)
            }

            // EditText 초기화
            etPetName.text?.clear()
        }
    }

    private fun createChip(tag: String): Chip = with(binding) {
        val chip = Chip(mainActivity, null, R.style.CustomChip)
        chip.apply {
            text = "#$tag"
            setChipBackgroundColorResource(R.color.main_color)
            setTextColor(ContextCompat.getColor(mainActivity, R.color.white))

            isCloseIconVisible = true // x 아이콘을 버튼을 보이게 설정
            setOnCloseIconClickListener {
                chipGroup.removeView(chip) // 클릭 시 Chip을 삭제합니다.
            }
        }
        // 여기서 원하는 스타일링을 할 수 있습니다.

        return chip
    }
    
    /**
     * 게시물 등록 관련 입력이 유효한가
     */
    private fun isValidInput(): Boolean = with(binding) {
        return if (etBoardContent.text.isNullOrBlank()) {
            mainActivity.showSnackbar("내용을 입력해주세요.")
            false
        } else {
            true
        }
    }
}
