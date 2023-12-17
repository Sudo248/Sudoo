package com.sudo248.sudoo.ui.activity.splash

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), ViewController {
    override val layoutId: Int = R.layout.activity_splash
    override val viewModel: SplashViewModel by viewModels()

    override fun initView() {
        viewModel.viewController = this
    }

    override fun isGrantedLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}