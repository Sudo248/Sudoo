package com.sudo248.sudoo.ui.activity.ar

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.ar.core.Plane
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ActivityArBinding
import com.sudo248.sudoo.ui.util.DeviceUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArActivity : BaseActivity<ActivityArBinding, ArViewModel>() {
    override val viewModel: ArViewModel by viewModels()

    override val enableStateScreen: Boolean = true

    private lateinit var arFragment: ArFragment
    private var modelRenderable: ModelRenderable? = null
    private var isDetectedPlane = false

    override fun initView() {
        if (!DeviceUtils.isDeviceSupportAR(this)) {
            Toast.makeText(applicationContext, R.string.device_not_supported, Toast.LENGTH_LONG)
                .show()
        }
        setupArFragment()
    }

    private fun initModelRenderable(source: Uri) {

    }

    private fun setupArFragment() {
        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment
        arFragment.arSceneView.scene.addOnUpdateListener {
            shouldShowModel()
        }
        arFragment.arSceneView.planeRenderer.isVisible = false
    }

    private fun shouldShowModel() {
        arFragment.arSceneView.arFrame?.getUpdatedTrackables(Plane::class.java)
            ?.firstOrNull { it.trackingState == TrackingState.TRACKING }?.let {
                isDetectedPlane = true
                drawModel()
            }
    }

    private fun drawModel() {

    }
}