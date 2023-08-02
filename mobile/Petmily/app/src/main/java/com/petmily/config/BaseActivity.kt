package com.petmily.config

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_FADE
import com.google.android.material.snackbar.Snackbar
import com.petmily.R
import com.petmily.util.NetworkUtil

// 액티비티의 기본을 작성, 뷰 바인딩 활용
abstract class BaseActivity<B : ViewBinding>(private val inflate: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B
        private set
//    lateinit var mLoadingDialog: LoadingDialog

    // 뷰 바인딩 객체를 받아서 inflate해서 화면을 만들어줌.
    // 즉 매번 onCreate에서 setContentView를 하지 않아도 됨.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    // 로딩 다이얼로그, 즉 로딩창을 띄워줌.
    // 네트워크가 시작될 때 사용자가 무작정 기다리게 하지 않기 위해 작성.
//    fun showLoadingDialog(context: Context) {
//        mLoadingDialog = LoadingDialog(context)
//        mLoadingDialog.show()
//    }
//    // 띄워 놓은 로딩 다이얼로그를 없앰.
//    fun dismissLoadingDialog() {
//        if (mLoadingDialog.isShowing) {
//            mLoadingDialog.dismiss()
//        }
//    }

    fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).apply {
            view.setBackgroundColor(resources.getColor(R.color.main_color))
            anchorView = findViewById(R.id.bottom_navigation)
            animationMode = ANIMATION_MODE_FADE
        }.show()
    }

    fun isNetworkConnected(): Boolean {
        if (NetworkUtil.isInternetConnected(this)) return true

        showSnackbar("인터넷 연결을 확인해주세요.")
        return false
    }
}
