package com.petmily.presentation.view

import android.os.Bundle
import android.text.TextUtils.replace
import com.petmily.R
import com.petmily.config.BaseActivity
import com.petmily.databinding.ActivityMainBinding
import com.petmily.presentation.view.certification.login.LoginFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_main, LoginFragment()).commit()
    }
}
