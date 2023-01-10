package com.zht.kotlin.cameraX

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.*
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import java.io.ByteArrayOutputStream

/**
 * @Date   2023/1/9 18:11
 * @Author zhanghaitao
 * @Description
 */
open class CameraActivity : AppCompatActivity() {

    private val TAG = "CameraActivity"
    private val FILENAME_FORMAT = "pictrue_%s.jpg"

    private val REQUEST_CODE = 0

    lateinit var previewView: PreviewView
    var imageCapture: ImageCapture? = null

    var onTakeYuv: Boolean = false

    fun startCamera() {
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

    private fun startCamera(granted: Boolean) {
        if (!granted) {
            Toast.makeText(this, "权限未开启，相机不可用", Toast.LENGTH_SHORT).show()
            return
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview 预览参数配置
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // 图片分析（实时获取预览数据）
            val imageAnalyzerBuilder = ImageAnalysis.Builder()
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                // 是否旋转分析器中得到的图片
//                imageAnalyzerBuilder.setOutputImageRotationEnabled(true)
//            }
            val imageAnalyzer = imageAnalyzerBuilder
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)//设置输出代码格式
                // OUTPUT_IMAGE_FORMAT_YUV_420_888 对应数据为：
                //      ImageProxy.getPlanes()[0].buffer // Y
                //      ImageProxy.getPlanes()[1].buffer // U
                //      ImageProxy.getPlanes()[2].buffer // V
                // OUTPUT_IMAGE_FORMAT_RGBA_8888 对应数据为：
                //      ImageProxy.getPlanes()[0].buffer // alpha
                //      ImageProxy.getPlanes()[1].buffer // red
                //      ImageProxy.getPlanes()[2].buffer // green
                //      ImageProxy.getPlanes()[3].buffer // blue
                .setTargetRotation(Surface.ROTATION_0) // 允许旋转后 得到图片的旋转设置
                .build()
                .also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(this),
                        ImageAnalysis.Analyzer { imageProxy ->


                            Log.e(TAG,
                                String.format("Analyzer(%d,%d)",
                                    imageProxy.width,
                                    imageProxy.height))
                            Log.e(TAG,
                                String.format("Analyzer PreviewView(%d,%d)",
                                    previewView.width,
                                    previewView.height))
                            try {
                                for (i in 0 until previewView.childCount){
                                    Log.e(TAG, "${previewView.get(i)}" )
                                    Log.e(TAG,
                                        String.format("Analyzer PreviewView[${i}](%d,%d)",
                                            previewView.get(i).width,
                                            previewView.get(i).height))
                                }

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            // 处理数据
                            if (!onTakeYuv) {
                                Log.e(TAG, "startAnalyzer")
                                startAnalyzer()
                                createBitmapFromImageProxy(imageProxy)?.apply {
                                    onCatchPreviewImage(this)
                                }
                                stopAnalyzer()
                            }
                            Log.e(TAG, "ImageProxy close")
                            imageProxy.close()
                        })
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // 在绑定前进行解绑
                cameraProvider.unbindAll()
                // 绑定生命周期和UseCase
                cameraProvider.bindToLifecycle(this,
                    cameraSelector, imageAnalyzer, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun setContentView(layoutResID: Int) {
        val view = LayoutInflater.from(this).inflate(layoutResID, null, false)
        super.setContentView(inflateCamera(view, null))
    }

    override fun setContentView(view: View?) {
        super.setContentView(inflateCamera(view, null))
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(inflateCamera(view, params))
    }

    private fun inflateCamera(view: View?, params: ViewGroup.LayoutParams?): View {
        previewView = PreviewView(this)
        val container = FrameLayout(this)
        val previewLayoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT)
        container.addView(previewView, previewLayoutParams)
        container.addView(view, params ?: previewLayoutParams)
        previewView.scaleType = PreviewView.ScaleType.FIT_START
        return container
    }

    private fun requestPermissions(permissions: Array<String?>) {
        var permissionGranted = true
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission!!)
                == PackageManager.PERMISSION_DENIED
            ) {
                permissionGranted = false
                break
            }
        }
        if (permissionGranted) {
            startCamera(true)
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE) {
            return
        }
        if (grantResults == null || grantResults.isEmpty()) {
            startCamera(false)
            return
        }
        var permissionGranted = true
        for (grantResult in grantResults) {
            if (PackageManager.PERMISSION_DENIED == grantResult) {
                permissionGranted = false
                break
            }
        }
        startCamera(permissionGranted)
    }

    fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = String.format(FILENAME_FORMAT, System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                }
            }
        )
    }

    private fun startAnalyzer() {
        onTakeYuv = true
    }

    private fun stopAnalyzer() {
        onTakeYuv = false
    }

    open fun onCatchPreviewImage(bitmap: Bitmap) {

    }

    /**
     * YUV : 分为三个分量，“Y”表示的是明亮度（Luminance或Luma），也就是灰度值；
     *       而“U”和“V” 表示的则是色度（Chrominance或Chroma），作用是用于指定像素的颜色。
     *
     * YUV420SP-NV21 格式图片（图片宽高：4*4）示例：
     * YYYY
     * YYYY
     * YYYY
     * YYYY
     * VUVU
     * VUVU
     */
    private fun createBitmapFromImageProxy(imageProxy: ImageProxy?): Bitmap? {
        imageProxy?.let {
            if (imageProxy.format != ImageFormat.YUV_420_888) {
                Log.e(TAG, "format is not YUV_420_888")
                return null
            }
            var byteArrayOutputStream: ByteArrayOutputStream? = null
            try {
                val width = imageProxy.width
                val height = imageProxy.height

                // 拿到YUV数据
                val yBuffer = imageProxy.planes[0].buffer
                val uBuffer = imageProxy.planes[1].buffer
                val vBuffer = imageProxy.planes[2].buffer

                val pixels = (width * height * 1.5f).toInt()//为什么需要乘以1.5?查找NV21的编码格式
                val yuvArrayNV21 = ByteArray(pixels) // 转换后的数据

                var index = 0

                // 复制Y的数据
                val yRowStride: Int = imageProxy.planes[0].rowStride
                val yPixelStride: Int = imageProxy.planes[0].pixelStride
                for (y in 0 until height) {
                    for (x in 0 until width) {
                        val bufferIndex = y * yRowStride + x * yPixelStride
                        yuvArrayNV21[index++] = yBuffer[bufferIndex]
                    }
                }

                // 复制U/V数据
                val uvRowStride: Int = imageProxy.planes[1].rowStride
                val uvPixelStride: Int = imageProxy.planes[1].pixelStride
                val uvWidth = width / 2
                val uvHeight = height / 2

                for (y in 0 until uvHeight) {
                    for (x in 0 until uvWidth) {
                        val bufferIndex = y * uvRowStride + x * uvPixelStride
                        yuvArrayNV21[index++] = vBuffer[bufferIndex]
                        yuvArrayNV21[index++] = uBuffer[bufferIndex]
                    }
                }
                // YuvImage 仅支持 YUY2和NV21格式的数据
                val yuvImage = YuvImage(yuvArrayNV21, ImageFormat.NV21, width, height, null)

                byteArrayOutputStream = ByteArrayOutputStream()
                yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, null)
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (t: Throwable) {
                t.printStackTrace()
            } finally {
                try {
                    byteArrayOutputStream?.close()
                } catch (e: java.io.IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
    }


}

