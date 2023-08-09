package com.petmily.presentation.view.dialog

import android.animation.Animator
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieDrawable
import com.petmily.R
import com.petmily.databinding.CustomDrawingDialogBinding
import com.petmily.databinding.CustomDrawingDialogBinding.inflate
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.ShopViewModel
import kotlinx.coroutines.launch

private const val TAG = "Petmily_DrawingDialog"

class DrawingDialog(private val context: Context, private val shopViewModel: ShopViewModel) : Dialog(context) {
    
    private lateinit var binding: CustomDrawingDialogBinding
    
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    
        // 배경 투명하게 변경
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initFirstLottie()
    }
    
    /**
     * 최초 애니메이션
     */
    private fun initFirstLottie() = with(binding) {
        lottieShow.apply {
            playAnimation()
            repeatCount = LottieDrawable.INFINITE
            speed = 1.2F
        }
    }
    
    
    /**
     * 아이템 뽑기 요청 결과가 들어오면 최초 애니메이션 종료
     */
    fun stopFirstLottie() = with(binding) {
        lottieShow.cancelAnimation()
    }
    
    
    /**
     * 꽝 걸렸을때 나타날 화면
     */
    fun initBoomLottie() = with(binding) {
        tvBoom.visibility = View.VISIBLE
        
        lottieShow.apply {
            setAnimation(R.raw.ani_boom)
            repeatCount = LottieDrawable.INFINITE // 무한 반복
            playAnimation()
        }
    }
    
    /**
     * 아이템이 걸렸을때 나타날 화면
     */
    fun initWinView() = with(binding){
        clWin.visibility = View.VISIBLE
    }
    
}