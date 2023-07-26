package com.petmily.presentation.view.curation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() = with(binding) {
        dogAdapter = CurationAdapter().apply {
        }
        catAdapter = CurationAdapter()
        etcAdapter = CurationAdapter()

        rcvCurationDog.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        rcvCurationCat.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        rcvCurationEtc.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
}
