package com.sudoo.shipment.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.sudo248.base_android.base.BaseActivity
import com.sudoo.shipment.R
import com.sudoo.shipment.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, OrderDetailViewModel>() {
    override val viewModel: OrderDetailViewModel by viewModels()
    override fun initView() {
        TODO("Not yet implemented")
    }

}