package com.petmily.presentation.view.store

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.petmily.R
import com.petmily.config.BaseFragment
import com.petmily.databinding.FragmentPurchaseBinding
import com.petmily.presentation.view.MainActivity
import com.petmily.presentation.view.dialog.DrawingDialog
import com.petmily.presentation.viewmodel.MainViewModel
import com.petmily.presentation.viewmodel.ShopViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PurchaseFragment :
    BaseFragment<FragmentPurchaseBinding>(FragmentPurchaseBinding::bind, R.layout.fragment_purchase) {

    private lateinit var mainActivity: MainActivity

    private val mainViewModel: MainViewModel by activityViewModels()
    private val shopViewModel: ShopViewModel by viewModels()

    private lateinit var dialog: DrawingDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initLottie()
        initObserve()
    }

    private fun initView() = with(binding) {
        lottieRingCoin.setOnClickListener {
            lottieRingCoin.apply {
                playAnimation()
                speed = 1.5F
                addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        showDialog("ring")
                        requestItem("ring")
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                })
            }
        }

        lottieBadgeCoin.setOnClickListener {
            lottieBadgeCoin.apply {
                playAnimation()
                speed = 1.5F
                addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        showDialog("badge")
                        requestItem("badge")
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                })
            }
        }

        lottieCoverCoin.setOnClickListener {
            lottieCoverCoin.apply {
                playAnimation()
                speed = 1.5F
                addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        showDialog("cover")
                        requestItem("cover")
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                })
            }
        }

        lottieAllCoin.setOnClickListener {
            lottieAllCoin.apply {
                playAnimation()
                speed = 1.5F
                addAnimatorListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {
                        showDialog("all")
                        requestItem("all")
                    }
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationRepeat(animation: Animator?) {}
                })
            }
        }
    }

    /**
     *   아이템 뽑기 요청
     */
    private fun requestItem(item: String) {
        shopViewModel.requestItem(item, mainViewModel)
    }

    /**
     * 다이얼로그 띄우고 동시에 API요청으로 상품 결과 수신
     */
    private fun showDialog(item: String) {
        // 다이얼로그 show
        context?.let { // context가 null이 아닐 때만 다이얼로그를 띄웁니다.
            dialog = DrawingDialog(it, shopViewModel)
            dialog.show()
        }
    }

    private fun initObserve() = with(shopViewModel) {
        // 아이템 뽑기 결과 (꽝, 성공 분기)
        resultItem.observe(viewLifecycleOwner) {
            dialog.stopFirstLottie(it)
        }
    }

    private fun initLottie() = with(binding) {
        lottieLeft.apply {
            // 애니메이션 재생
            playAnimation()

            addAnimatorListener(object : Animator.AnimatorListener {

                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    // 애니메이션이 종료된 후 4초 후에 다시 애니메이션을 재생
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(500)
                        playAnimation()
                    }
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
        }

        lottieMiddle.apply {
            playAnimation()

            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    // 애니메이션이 종료된 후 4초 후에 다시 애니메이션을 재생
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        playAnimation()
                    }
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
        }

        lottieRight.apply {
            playAnimation()

            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    // 애니메이션이 종료된 후 4초 후에 다시 애니메이션을 재생
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        playAnimation()
                    }
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })
        }
    }

    override fun onPause() = with(binding) {
        super.onPause()
    }
}
