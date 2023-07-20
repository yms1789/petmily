package com.petmily.presentation.view

import android.os.Bundle
import com.petmily.R
import com.petmily.config.BaseActivity
import com.petmily.databinding.ActivityMainBinding
import com.petmily.presentation.view.certification.join.JoinFragment

class MainActivity : BaseActivity<ActivityMainBinding> (ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.frame_layout_main, JoinFragment())
            .commit()
    }
}
