package com.petmily.presentation.view

import android.os.Bundle
import androidx.fragment.app.commit
import com.petmily.R
import com.petmily.config.BaseActivity
import com.petmily.databinding.ActivityMainBinding
import com.petmily.presentation.view.certification.login.LoginFragment
import com.petmily.presentation.view.info.user.UserInfoInputFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(R.id.frame_layout_main, UserInfoInputFragment())
        }
    }
}
