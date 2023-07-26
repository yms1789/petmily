package com.petmily.presentation.view.curation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentCurationDetailBinding
import com.petmily.presentation.view.MainActivity

class CurationDetailFragment :
    BaseFragment<FragmentCurationDetailBinding>(FragmentCurationDetailBinding::bind, R.layout.fragment_curation_detail) {
    private lateinit var mainActivity: MainActivity

    private lateinit var healthAdapter: CurationAdapter
    private lateinit var beautyAdapter: CurationAdapter
    private lateinit var trainingAdapter: CurationAdapter
    private lateinit var adoptAdapter: CurationAdapter

    private lateinit var snapHelper: LinearSnapHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() = with(binding) {
        healthAdapter = CurationAdapter().apply {
        }
        beautyAdapter = CurationAdapter()
        trainingAdapter = CurationAdapter()
        adoptAdapter = CurationAdapter()

        rcvCurationHealth.apply {
            adapter = healthAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(ItemSpacingDecoration(30))
        }

        rcvCurationBeauty.apply {
            adapter = beautyAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(ItemSpacingDecoration(30))
        }

        rcvCurationTraining.apply {
            adapter = trainingAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(ItemSpacingDecoration(30))
        }

        rcvCurationAdopt.apply {
            adapter = adoptAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(ItemSpacingDecoration(30))
        }

        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcvCurationHealth)
    }
}
