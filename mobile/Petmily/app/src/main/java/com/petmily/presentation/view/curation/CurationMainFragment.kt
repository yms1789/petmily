package com.petmily.presentation.view.curation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentCurationMainBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.CurationViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Curation

private const val TAG = "petmily_CurationMainFragment"

@SuppressLint("LongLogTag")
class CurationMainFragment :
    BaseFragment<FragmentCurationMainBinding>(FragmentCurationMainBinding::bind, R.layout.fragment_curation_main) {

    private lateinit var mainActivity: MainActivity

    private val curationViewModel: CurationViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var dogAdapter: CurationAdapter
    private lateinit var catAdapter: CurationAdapter
    private lateinit var etcAdapter: CurationAdapter

    private lateinit var snapHelperDog: LinearSnapHelper
    private lateinit var snapHelperCat: LinearSnapHelper
    private lateinit var snapHelperEtc: LinearSnapHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initView()
        initButton()
        initObserver()
        initSnapHelper()
    }
    
    private fun initView() = with(binding) {
        cdMoveDog.setOnClickListener {
            curationViewModel.fromCuration = "dog"
            mainActivity.changeFragment("curation detail")
        }
    
        cdMoveCat.setOnClickListener {
            curationViewModel.fromCuration = "cat"
            mainActivity.changeFragment("curation detail")
        }
    
        cdMoveEtc.setOnClickListener {
            curationViewModel.fromCuration = "etc"
            mainActivity.changeFragment("curation detail")
        }
    }

    private fun initSnapHelper() = with(binding) {
        snapHelperDog = LinearSnapHelper()
        snapHelperDog.attachToRecyclerView(rcvCurationDog)

        snapHelperCat = LinearSnapHelper()
        snapHelperCat.attachToRecyclerView(rcvCurationCat)

        snapHelperEtc = LinearSnapHelper()
        snapHelperEtc.attachToRecyclerView(rcvCurationEtc)
    }

    private fun initButton() = with(binding) {
        ivSearch.setOnClickListener {
            mainActivity.changeFragment("search")
        }

        ivNoti.setOnClickListener {
            mainActivity.changeFragment("notification")
        }
    }

    // Dog
    private fun initDogAdapter() = with(binding) {
        dogAdapter = CurationAdapter(curationViewModel.curationDogList.value).apply {
            itemClickListener = object : CurationAdapter.ItemClickListener {
                override fun onClick(view: View, curation: Curation, position: Int) {
                    Log.d(TAG, "onClick Dog Curation: $position | ${curation.curl}")
                    curationViewModel.webViewUrl = curation.curl
                    mainActivity.changeFragment("webView")
                }
            }
        }

        rcvCurationDog.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    // Cat
    private fun initCatAdapter() = with(binding) {
        catAdapter = CurationAdapter(curationViewModel.curationCatList.value).apply {
            itemClickListener = object : CurationAdapter.ItemClickListener {
                override fun onClick(view: View, curation: Curation, position: Int) {
                    curationViewModel.webViewUrl = curation.curl
                    mainActivity.changeFragment("webView")
                }
            }
        }

        rcvCurationCat.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    // ETC
    private fun initEtcAdapter() = with(binding) {
        etcAdapter = CurationAdapter(curationViewModel.curationEtcList.value).apply {
            itemClickListener = object : CurationAdapter.ItemClickListener {
                override fun onClick(view: View, curation: Curation, position: Int) {
                    curationViewModel.webViewUrl = curation.curl
                    mainActivity.changeFragment("webView")
                }
            }
        }

        rcvCurationEtc.apply {
            adapter = etcAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initObserver() = with(curationViewModel) {
        // Dog
        curationDogList.observe(viewLifecycleOwner) {
            Log.d(TAG, "requestCurationData - Dog List Size: ${it?.size}")
            initDogAdapter()
            if (!it.isNullOrEmpty()) devideDogCuration(it)
        }

        // Cat
        curationCatList.observe(viewLifecycleOwner) {
            Log.d(TAG, "requestCurationData - Cat List Size: ${it?.size}")
            initCatAdapter()
            if (!it.isNullOrEmpty()) devideCatCuration(it)
        }
        
        // Etc
        curationEtcList.observe(viewLifecycleOwner) {
            Log.d(TAG, "requestCurationData - Etc List Size: ${it?.size}")
            initEtcAdapter()
            if (!it.isNullOrEmpty()) devideEtcCuration(it)
        }
    }
}

/**
 * 리사이클러뷰 아이템 간격 조절
 */
class ItemSpacingDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            // left, right 값을 조절
            left = space
            right = space
        }
    }
}
