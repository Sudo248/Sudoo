package com.sudo248.sudoo.ui.activity.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.base_android.ktx.gone
import com.sudo248.base_android.ktx.visible
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ActivityMainBinding
import com.sudo248.sudoo.domain.common.Constants
import com.sudo248.sudoo.ui.ktx.createTempPictureUri
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), ViewController {
    override val layoutId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    private var deepLinkHandler: DeepLinkHandler? = null

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                viewModel.setImageUri(uri)
            } else {
                DialogUtils.showErrorDialog(
                    this,
                    "Pick image error"
                )
            }
        }

    private val takeImage = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            viewModel.getTakeImageUri()
        } else {
            viewModel.setImageUri(null)
            DialogUtils.showErrorDialog(
                this,
                "Take image error"
            )
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            viewModel.getCurrentLocation()
        }
    }

    private val listFragmentHideBottomNav = listOf(
        R.id.productDetailFragment,
        R.id.searchFragment,
        R.id.chatFragment,
        R.id.reviewListFragment,
        R.id.reviewFragment,
        R.id.orderFragment,
        R.id.orderDetailFragment
    )

    private val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
        if (destination.id in listFragmentHideBottomNav) {
            goneBottomNav()
        } else {
            showBottomNav()
        }
    }

    private lateinit var navController: NavController

    override fun initView() {
        requestPermission()
        viewModel.viewController = this
        val navHost = supportFragmentManager.findFragmentById(R.id.fcvMain) as NavHostFragment
        navController = navHost.navController
        navController.setGraph(R.navigation.main_nav)
        NavigationUI.setupWithNavController(
            binding.bottomNav,
            navController
        )

        setupBadgeCart()
    }
    // 2131230946

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.getStringExtra(Constants.Key.SCREEN)?.let {
            when(it) {
                Constants.Screen.DISCOVERY -> {
                    navController.popBackStack()
                    viewModel.getItemInCart()
                }
                Constants.Screen.PROMOTION -> {
                    binding.bottomNav.selectedItemId = R.id.promotionFragment
                }
                else -> {

                }
            }
        }

        intent?.data?.path?.let {
            deepLinkHandler?.onHandle(it)
        }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun observer() {
        super.observer()
        viewModel.countCartItem.observe(this) {
            setBadgeCart(it)
        }
    }

    fun showBottomNav() {
        if (binding.bottomNav.isGone) {
            binding.bottomNav.visible()
        }
    }

    fun goneBottomNav() {
        if (binding.bottomNav.isVisible) {
            binding.bottomNav.gone()
        }
    }

    override fun pickImage() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    override fun takeImage(uri: Uri) {
        takeImage.launch(uri)
    }

    override fun createTempPictureUri(): Uri {
        return (this as Context).createTempPictureUri()
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    fun setBadgeCart(count: Int) {
        val badge = binding.bottomNav.getOrCreateBadge(R.id.cartFragment)
        if (count > 0) {
            badge.isVisible = true
            badge.number = count
        } else {
            badge.isVisible = false
        }
    }

    private fun setupBadgeCart() {
        val badge = binding.bottomNav.getOrCreateBadge(R.id.cartFragment)
        badge.backgroundColor = getColor(R.color.primaryColor)
        badge.badgeTextColor = getColor(R.color.white)
        badge.verticalOffset = 3
    }

    fun setDeepLinkHandler(deepLinkHandler: DeepLinkHandler) {
        this.deepLinkHandler = deepLinkHandler
    }
}