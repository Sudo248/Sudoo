package com.sudoo.shipment.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.util.isNotEmpty
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.sudo248.base_android.base.BaseActivity
import com.sudo248.base_android.ktx.createActionIntentDirections
import com.sudoo.shipment.Constants
import com.sudoo.shipment.R
import com.sudoo.shipment.databinding.ActivityScanQrBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanQrActivity : BaseActivity<ActivityScanQrBinding, ScanQrViewModel>() {
    override val viewModel: ScanQrViewModel by viewModels()

    private var detector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null

    private val launcherPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {

            }
        }

    override fun initView() {

    }

    private fun initDetectorAndSources() {
        detector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()

        cameraSource = CameraSource.Builder(this, detector!!)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(p0: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@ScanQrActivity,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(binding.surfaceView.holder)
                    } else {
                        launcherPermission.launch(Manifest.permission.CAMERA)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                cameraSource?.stop()
            }

        })

        detector?.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.isNotEmpty()) {
                    val url = barcodes.valueAt(0).displayValue
                    navigateTo(OrderDetailActivity::class.createActionIntentDirections {
                        putExtra(Constants.ORDER_ID, getOrderIdFromUrl(url))
                    })
                }
            }
        })
    }

    private fun getOrderIdFromUrl(url: String): String {
        return url.substringAfterLast("/")
    }

    override fun onResume() {
        super.onResume()
        initDetectorAndSources()
    }

    override fun onPause() {
        super.onPause()
        cameraSource?.release();
    }
}