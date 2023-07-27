package com.petmily.presentation.view.gallery

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentGalleryBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel

class GalleryFragment :
    BaseFragment<FragmentGalleryBinding>(FragmentGalleryBinding::bind, R.layout.fragment_gallery) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var mainActivity: MainActivity
    private lateinit var galleryAdapter: GalleryAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initButton()
    }

    private fun initRecyclerView() = with(binding) {
        galleryAdapter = GalleryAdapter(viewLifecycleOwner, mainViewModel)

        rcvGallery.apply {
            layoutManager = GridLayoutManager(mainActivity, 3)
            adapter = galleryAdapter
        }
    }

    private fun initButton() = with(binding) {
        addBtn.setOnClickListener {
            mainViewModel.clearAddPhotoList()
            for (photo in mainViewModel.galleryList.value!!) {
                if (photo.isSelected.value!!) mainViewModel.addToAddPhotoList(photo)
            }
            mainActivity.changeFragment("add")
        }

        // 핸드폰 기기 back버튼
        mainActivity.onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mainActivity.changeFragment("add") // 어디서 왔냐? 저장 -> 분기
                }
            },
        )
    }
}
