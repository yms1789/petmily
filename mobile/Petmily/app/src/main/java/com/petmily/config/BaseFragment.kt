package com.petmily.config

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.petmily.R

// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int,
) : Fragment(layoutResId) {
    private var _binding: B? = null
//    lateinit var mLoadingDialog: LoadingDialog

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

//    fun showLoadingDialog(context: Context) {
//        mLoadingDialog = LoadingDialog(context)
//        mLoadingDialog.show()
//    }
//
//    fun dismissLoadingDialog() {
//        if (mLoadingDialog.isShowing) {
//            mLoadingDialog.dismiss()
//        }
//    }
}
