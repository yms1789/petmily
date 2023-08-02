package com.petmily.presentation.view.curation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentCurationMainBinding
import com.petmily.presentation.view.MainActivity

class CurationMainFragment :
    BaseFragment<FragmentCurationMainBinding>(FragmentCurationMainBinding::bind, R.layout.fragment_curation_main) {

    private lateinit var mainActivity: MainActivity

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

        initAdapter()
        initButton()
    }

    private fun initButton() = with(binding) {
        btnCurationMainDog.setOnClickListener {
            mainActivity.changeFragment("curation detail")
        }

        btnCurationMainCat.setOnClickListener {
            mainActivity.changeFragment("curation detail")
        }

        btnCurationMainEtc.setOnClickListener {
            mainActivity.changeFragment("curation detail")
        }
        
        ivSearch.setOnClickListener {
            mainActivity.changeFragment("search")
        }
    }

    private fun initAdapter() = with(binding) {
        dogAdapter = CurationAdapter().apply {
        }
        catAdapter = CurationAdapter()
        etcAdapter = CurationAdapter()

        rcvCurationDog.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(ItemSpacingDecoration(30))
        }

        rcvCurationCat.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(ItemSpacingDecoration(30))
        }

        rcvCurationEtc.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(ItemSpacingDecoration(30))
        }

        snapHelperDog = LinearSnapHelper()
        snapHelperCat = LinearSnapHelper()
        snapHelperEtc = LinearSnapHelper()
        snapHelperDog.attachToRecyclerView(rcvCurationDog)
        snapHelperCat.attachToRecyclerView(rcvCurationCat)
        snapHelperEtc.attachToRecyclerView(rcvCurationEtc)
    }
}

class ItemSpacingDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            // left, right 값을 조절
            left = space
            right = space
        }
    }
}
