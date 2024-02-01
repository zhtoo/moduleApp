package com.zht.modulehome.compose.util

import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt


/**
 * @Date   2024/1/22 15:28
 * @Author zhanghaitao
 * @Description
 */
@JvmOverloads
fun ComponentActivity.windowController(
    showStatusBar: Boolean = true,
    showNavigationBar: Boolean = true,
    @ColorInt statusBarBackgroundColor: Int = Color.TRANSPARENT,
    @ColorInt navigationBarBackgroundColor: Int = Color.TRANSPARENT,
    statusBarDarkIcon: Boolean = true,
    enableStatusBarSpace: Boolean = true,
    navigationBarDarkIcon: Boolean = false,
    enableNavigationBarSpace: Boolean = false,
) {
    window.decorView.postDelayed({
            Log.e(
                "log",
                "window.decorView size(${window.decorView.width},${window.decorView.height})"
            )
            Log.e("log", "window.decorView object(${window.decorView})")
            Log.e(
                "log",
                "window.contentView padding:(${window.decorView.paddingLeft},${window.decorView.paddingTop},${window.decorView.paddingRight},${window.decorView.paddingBottom})"
            )
        }, 16
    )

    val mContentView = window.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT) ?: return
    mContentView.postDelayed({
            Log.e(
                "log",
                "window.contentView size(${mContentView.width},${mContentView.height})"
            )
            Log.e("log", "window.contentView object:(${mContentView})")
            Log.e(
                "log",
                "window.contentView padding:(${mContentView.paddingLeft},${mContentView.paddingTop},${mContentView.paddingRight},${mContentView.paddingBottom})"
            )
        }, 16
    )

    // ComponentActivity.enableEdgeToEdge()


}

