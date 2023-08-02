package com.petmily.presentation.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.petmily.R
import com.petmily.databinding.CustomWithdrawalDialogBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel

class WithDrawalDialog(private val context: Context, private val mainViewModel: MainViewModel) : Dialog(context) {

    private lateinit var binding: CustomWithdrawalDialogBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CustomWithdrawalDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 배경 투명하게 변경
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        init()
        initBtn()
        initObserver()
    }

    private fun init() {
        mainActivity = context as MainActivity
    }

    private fun initObserver() = with(mainViewModel) {
        withDrawalCheck.observe(mainActivity) {
            // 통신 요청 -> 결과
            binding.btnWithdrawalOk.apply {
                isEnabled = it

                backgroundTintList = ColorStateList.valueOf(
                    if (it) {
                        resources.getColor(R.color.main_color)
                    } else {
                        Color.GRAY
                    },
                )
            }
        }
    }

    private fun initBtn() = with(binding) {
        // 비밀번호 인증 버튼
        btnWithdrawalAuth.setOnClickListener {
            /* todo
                비밀번호 체크 통신 요청(이메일, 비밀번호) 후 결과 받으면 옵져버로 "withDrawalCheck"를 관찰하고 있다가
                true면 탈퇴 버튼 on -> 서버에 회원정보 삭제 요청(이메일, 비밀번호)
             */
        }

        btnWithdrawalOk.setOnClickListener {
            dismiss()
        }

        btnWithdrawalCancle.setOnClickListener {
            dismiss()
        }
    }
}
