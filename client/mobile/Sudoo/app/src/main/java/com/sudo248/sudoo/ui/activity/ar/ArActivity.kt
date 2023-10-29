package com.sudo248.sudoo.ui.activity.ar

import android.graphics.PointF
import android.net.Uri
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.ar.core.Plane
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.base_android.core.UiState
import com.sudo248.base_android.event.SingleEvent
import com.sudo248.base_android.utils.DialogUtils
import com.sudo248.sudoo.R
import com.sudo248.sudoo.databinding.ActivityArBinding
import com.sudo248.sudoo.ui.ktx.showErrorDialog
import com.sudo248.sudoo.ui.util.BundleKeys
import com.sudo248.sudoo.ui.util.DeviceUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        getSourceFromIntent()?.let {
            viewModel.getModelResource(it)
        }
    }

    override fun observer() {
        viewModel.modelSource.observe(this) {
            initModelRenderable(it)
        }
    }

    private fun getSourceFromIntent(): String? {
        return intent.getStringExtra(BundleKeys.SOURCE_AR)
    }

    private fun initModelRenderable(source: Uri) {
        ModelRenderable.builder()
            .setSource(this, source, true)
            .build()
            .thenAccept {
                modelRenderable = it
                renderModelOrElse()
            }
            .exceptionally {
                viewModel.error = SingleEvent(it.message)
                viewModel.setState(UiState.ERROR)
                return@exceptionally null;
            }
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
                renderModelOrElse()
            }
    }

    private fun renderModelOrElse() {
        if (isDetectedPlane && modelRenderable != null) {
            renderModel(modelRenderable!!)
        }
    }

    private fun renderModel(renderable: ModelRenderable) {
        val centerPoint = resources.displayMetrics.run {
            PointF(widthPixels / 2f, heightPixels / 2f)
        }

        lifecycleScope.launch {
            while (true) {
                val results = arFragment.arSceneView.session?.update()?.hitTest(centerPoint.x, centerPoint.y)
                if (!results.isNullOrEmpty()) {
                    val anchor = AnchorNode(results.first().createAnchor())
                    anchor.setParent(arFragment.arSceneView.scene)
                    // attaching the anchorNode with the TransformableNode
                    val model = TransformableNode(arFragment.transformationSystem)
                    model.setParent(anchor)
                    /*model.scaleController.maxScale = 0.6f
                    model.scaleController.minScale = 0.6f*/
                    model.renderable = renderable
                    model.select()
                    return@launch
                } else {
                    delay(100)
                }
            }
        }
    }

    override fun onStateError() {
        super.onStateError()
        viewModel.error.run {
            val message = getValueIfNotHandled()
            if (!message.isNullOrEmpty()) {
                DialogUtils.showErrorDialog(
                    context = this@ArActivity,
                    message = message
                )
            }
        }
    }
}