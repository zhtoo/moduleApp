package com.zht.kotlin.cameraX

import android.Manifest
import android.content.ContentValues
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.*
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker

/**
 * @Date   2023/1/9 18:11
 * @Author zhanghaitao
 * @Description
 */
open class VideoCameraActivity : BaseCameraActivity() {

    private val TAG = "CameraActivity"
    private val FILENAME_FORMAT = "video_%s.mp4"

    var videoCapture: VideoCapture<Recorder>? = null

    fun checkPermissionsAndStartCamera() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            requestPermissions(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ))
        } else {
            requestPermissions(arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ))
        }
    }

    override fun onPermissionsGranted(granted: Boolean) {
        if (!granted) {
            Toast.makeText(this, "权限未开启，相机不可用", Toast.LENGTH_SHORT).show()
            return
        }
        startCamera()
    }


    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview 预览参数配置
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_16_9)
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            val recorder = Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                .build()

            videoCapture = VideoCapture.withOutput(recorder)

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 在绑定前进行解绑
                cameraProvider.unbindAll()
                // 绑定生命周期和UseCase
                cameraProvider.bindToLifecycle(this,
                    cameraSelector, preview, videoCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private var recording: Recording? = null

    open fun startVideo() {
        val videoCapture = this.videoCapture ?: return

        // create and start a new recording session
        val name = String.format(FILENAME_FORMAT, System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video")
            }
        }

        val mediaStoreOutputOptions = MediaStoreOutputOptions
            .Builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            .setContentValues(contentValues)
            .build()

        recording = videoCapture.output
            .prepareRecording(this, mediaStoreOutputOptions)
            .apply {
                if (PermissionChecker.checkSelfPermission(this@VideoCameraActivity,
                        Manifest.permission.RECORD_AUDIO) ==
                    PermissionChecker.PERMISSION_GRANTED
                ) {
                    withAudioEnabled()
                }
            }
            .start(ContextCompat.getMainExecutor(this)) { recordEvent ->
                when (recordEvent) {
                    is VideoRecordEvent.Start -> {//开始录制

                    }
                    is VideoRecordEvent.Finalize -> {//结束录制
                        if (!recordEvent.hasError()) {
                            val msg = "Video capture succeeded: " +
                                    "${recordEvent.outputResults.outputUri}"
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT)
                                .show()
                            Log.e(TAG, msg)
                        } else {
                            recording?.close()
                            recording = null
                            Log.e(TAG, "Video capture ends with error: " +
                                    "${recordEvent.error}")
                        }
                    }
                }
            }
    }

    open fun pauseVideo() {
        recording?.pause()
    }

    open fun stopVideo() {
        recording?.stop()
        recording = null
    }


}

