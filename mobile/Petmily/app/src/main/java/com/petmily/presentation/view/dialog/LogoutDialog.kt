package com.petmily.presentation.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.petmily.databinding.CustomLogoutDialogBinding
import com.petmily.databinding.CustomLogoutDialogBinding.inflate
import com.petmily.presentation.viewmodel.MainViewModel

class LogoutDialog(context: Context, mainViewModel: MainViewModel) : Dialog(context) {

    private lateinit var binding: CustomLogoutDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        // 배경 투명하게 변경
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initBtn()
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
