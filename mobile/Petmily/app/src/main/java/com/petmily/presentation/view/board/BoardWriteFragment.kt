package com.petmily.presentation.view.board

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentBoardWriteBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.util.CheckPermission
import com.petmily.util.GalleryUtil

class BoardWriteFragment :
    BaseFragment<FragmentBoardWriteBinding>(FragmentBoardWriteBinding::bind, R.layout.fragment_board_write) {

    private lateinit var mainActivity: MainActivity
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var galleryUtil: GalleryUtil
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
        initAdapter()
        initButton()
        initEditText()
    }

    private fun initAdapter() = with(binding) {
        imageAdapter = ImageAdapter()

        rcvSelectImage.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initButton() = with(binding) {
    }

    private fun initEditText() = with(binding) {
        etPetName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                return if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addChipsFromEditText()
                    true
                } else {
                    false
                }
            }
        })
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

    private fun createChip(tag: String): Chip {
        val chip = Chip(mainActivity)
        chip.text = "#$tag"
        // 여기서 원하는 스타일링을 할 수 있습니다.
        return chip
    }
}
