package com.sudoo.shipment.ui

import androidx.activity.viewModels
import com.sudo248.base_android.base.BaseActivity
import com.sudoo.shipment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()
    override val enableStateScreen: Boolean = true
    override fun initView() {
        viewModel.signIn()
    }
}