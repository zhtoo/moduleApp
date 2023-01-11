package com.zht.kotlin.cameraX

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * @Date   2023/1/9 18:11
 * @Author zhanghaitao
 * @Description
 */
abstract class CameraPermissionActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS_CODE = 0x0011

    open fun requestPermissions(permissions: Array<String?>) {
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
            onPermissionsGranted(true)
        } else {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_PERMISSIONS_CODE) {
            return
        }
        if (grantResults == null || grantResults.isEmpty()) {
            onPermissionsGranted(false)
            return
        }
        var permissionGranted = true
        for (grantResult in grantResults) {
            if (PackageManager.PERMISSION_DENIED == grantResult) {
                permissionGranted = false
                break
            }
        }
        onPermissionsGranted(permissionGranted)
    }

    abstract fun onPermissionsGranted(granted: Boolean)

}

