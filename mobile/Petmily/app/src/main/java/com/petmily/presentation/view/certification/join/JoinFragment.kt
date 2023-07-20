package com.petmily.presentation.view.certification.join

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentJoinBinding
import com.petmily.presentation.view.MainActivity

class JoinFragment : BaseFragment<FragmentJoinBinding>(FragmentJoinBinding::bind, R.layout.fragment_join) {

    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // email 선택 adapter
        val emailAdapter = ArrayAdapter(mainActivity, R.layout.dropdown_email, emailList)
        binding.actEmail.setAdapter(emailAdapter)
    }

    companion object {
        val emailList = arrayOf(
            "naver.com",
            "gmail.com",
            "yahoo.com",
            "nate.com",
            "hanmail.com",
        )
    }
}
