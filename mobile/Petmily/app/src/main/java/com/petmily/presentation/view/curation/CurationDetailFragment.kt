package com.petmily.presentation.view.curation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentCurationDetailBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.CurationViewModel
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.repository.dto.Curation

private const val TAG = "petmily_CurationDetailFragment"

@SuppressLint("LongLogTag")
class CurationDetailFragment :
    BaseFragment<FragmentCurationDetailBinding>(FragmentCurationDetailBinding::bind, R.layout.fragment_curation_detail) {
    private lateinit var mainActivity: MainActivity
    
    private val curationViewModel: CurationViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var healthAdapter: CurationAdapter
    private lateinit var beautyAdapter: CurationAdapter
    private lateinit var feedAdapter: CurationAdapter
    private lateinit var adoptAdapter: CurationAdapter

    private lateinit var snapHelperHealth: LinearSnapHelper
    private lateinit var snapHelperBeauty: LinearSnapHelper
    private lateinit var snapHelperFeed: LinearSnapHelper
    private lateinit var snapHelperAdopt: LinearSnapHelper
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        Log.d(TAG, "onViewCreated: ${curationViewModel.dogFeedList}")
        Log.d(TAG, "onViewCreated: ${curationViewModel.dogAdoptList}")
    
    
        initSnapHelper()
        initHealthAdapter()
        initBeautyAdapter()
        initFeedAdapter()
        initAdoptAdapter()
        initButton()
    }
    
    private fun initSnapHelper() = with(binding) {
        snapHelperHealth = LinearSnapHelper()
        snapHelperBeauty = LinearSnapHelper()
        snapHelperFeed = LinearSnapHelper()
        snapHelperAdopt = LinearSnapHelper()
    
        snapHelperHealth.attachToRecyclerView(rcvCurationHealth)
        snapHelperBeauty.attachToRecyclerView(rcvCurationBeauty)
        snapHelperFeed.attachToRecyclerView(rcvCurationFeed)
        snapHelperAdopt.attachToRecyclerView(rcvCurationAdopt)
    }

    private fun initButton() = with(binding) {
        ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
    
    private fun initHealthAdapter() = with(binding) {
        when (curationViewModel.fromCuration) {
            "dog" -> healthAdapter = CurationAdapter(curationViewModel.dogHealthList)
            "cat" -> healthAdapter = CurationAdapter(curationViewModel.catHealthList)
            "etc" -> healthAdapter = CurationAdapter(curationViewModel.etcHealthList)
        }

        healthAdapter.apply {
            itemClickListener = object : CurationAdapter.ItemClickListener {
                override fun onClick(view: View, curation: Curation, position: Int) {
                    curationViewModel.webViewUrl = curation.curl
                    mainActivity.changeFragment("webView")
                }
            }
        }
    
        rcvCurationHealth.apply {
            adapter = healthAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    
    private fun initBeautyAdapter() = with(binding) {
        when (curationViewModel.fromCuration) {
            "dog" -> beautyAdapter = CurationAdapter(curationViewModel.dogBeautyList)
            "cat" -> beautyAdapter = CurationAdapter(curationViewModel.catBeautyList)
            "etc" -> beautyAdapter = CurationAdapter(curationViewModel.etcBeautyList)
        }
    
        beautyAdapter.apply {
            itemClickListener = object : CurationAdapter.ItemClickListener {
                override fun onClick(view: View, curation: Curation, position: Int) {
                    curationViewModel.webViewUrl = curation.curl
                    mainActivity.changeFragment("webView")
                }
            }
        }
        
        rcvCurationBeauty.apply {
            adapter = beautyAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    
    private fun initFeedAdapter() = with(binding) {
        when (curationViewModel.fromCuration) {
            "dog" -> feedAdapter = CurationAdapter(curationViewModel.dogFeedList)
            "cat" -> feedAdapter = CurationAdapter(curationViewModel.catFeedList)
            "etc" -> feedAdapter = CurationAdapter(curationViewModel.etcFeedList)
        }
    
        feedAdapter.apply {
            itemClickListener = object : CurationAdapter.ItemClickListener {
                override fun onClick(view: View, curation: Curation, position: Int) {
                    curationViewModel.webViewUrl = curation.curl
                    mainActivity.changeFragment("webView")
                }
            }
        }
    
        rcvCurationFeed.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    
    private fun initAdoptAdapter() = with(binding) {
        when (curationViewModel.fromCuration) {
            "dog" -> adoptAdapter = CurationAdapter(curationViewModel.dogAdoptList)
            "cat" -> adoptAdapter = CurationAdapter(curationViewModel.catAdoptList)
            "etc" -> adoptAdapter = CurationAdapter(curationViewModel.etcAdoptList)
        }
    
        adoptAdapter.apply {
            itemClickListener = object : CurationAdapter.ItemClickListener {
                override fun onClick(view: View, curation: Curation, position: Int) {
                    curationViewModel.webViewUrl = curation.curl
                    mainActivity.changeFragment("webView")
                }
            }
        }
    
        rcvCurationAdopt.apply {
            adapter = adoptAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
