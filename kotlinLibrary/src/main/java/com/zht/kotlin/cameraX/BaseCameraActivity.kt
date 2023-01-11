package com.zht.kotlin.cameraX

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.camera.view.PreviewView

/**
 * @Date   2023/1/9 18:11
 * @Author zhanghaitao
 * @Description https://developer.android.com/codelabs/camerax-getting-started?hl=zh-cn#0
 *
 * https://developer.android.google.cn/training/camerax/configuration?hl=zh_cn
 */
open abstract class BaseCameraActivity : CameraPermissionActivity() {

    open lateinit var previewView: PreviewView

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


}

