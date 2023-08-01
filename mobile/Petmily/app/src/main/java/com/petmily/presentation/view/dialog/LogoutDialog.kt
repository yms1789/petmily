package com.petmily.presentation.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.petmily.databinding.CustomLogoutDialogBinding

class LogoutDialog(context: Context) : DialogFragment() {

    private var _binding: CustomLogoutDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = CustomLogoutDialogBinding.inflate(inflater, container, false)
        val view = binding.root

        initBtn()

        return view
    }

    private fun initBtn() = with(binding) {
        btnLogoutOk.setOnClickListener {
            dismiss()
        }

        btnLogoutCancle.setOnClickListener {
            dismiss()
        }
    }
}
