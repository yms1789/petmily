package com.petmily.presentation.view.store

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.system.Os.bind
import android.view.View
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPurchaseBinding
import com.petmily.presentation.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PurchaseFragment :
    BaseFragment<FragmentPurchaseBinding>(FragmentPurchaseBinding::bind, R.layout.fragment_purchase) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLottie()
    }

    private fun initLottie() = with(binding) {
        lottieItem.apply {
//            setAnimation(R.raw.your_animation)
            // 애니메이션 재생
            playAnimation()

            // 애니메이션 일시정지
            // lottieAnimationView.pauseAnimation()

            // 애니메이션 정지 (처음으로 리셋)
            // lottieAnimationView.cancelAnimation()

            // 특정 프레임으로 이동
            // lottieAnimationView.frame = 60

            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}

                override fun onAnimationEnd(animation: Animator?) {
                    // 애니메이션이 종료된 후 4초 후에 다시 애니메이션을 재생
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(4000)
                        playAnimation()
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {}

                override fun onAnimationRepeat(animation: Animator?) {}
            })
        }
    }

    override fun onPause() = with(binding) {
        lottieItem.pauseAnimation()
        super.onPause()
    }
}
