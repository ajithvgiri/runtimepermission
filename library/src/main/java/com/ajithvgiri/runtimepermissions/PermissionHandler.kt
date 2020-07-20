package com.ajithvgiri.runtimepermissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat

// Single Runtime Permission handling
fun Context.checkRTPermission(permission: String, completion: PermissionCheckCompletion) {
    // If permission is not granted
    if (shouldAskPermission(permission)) {
        (this as? Activity)?.let {
            if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                completion(CheckPermissionResult.PermissionPreviouslyDenied)
            } else {
                if (isFirstTimeAskingPermission(permission)) {
                    firstTimeAskingPermission(permission, false)
                    completion(CheckPermissionResult.PermissionAsk)
                } else {
                    completion(CheckPermissionResult.PermissionPermanentlyDenied)
                }
            }
        } ?: kotlin.run {
            completion(CheckPermissionResult.PermissionAsk)
        }
    } else {
        completion(CheckPermissionResult.PermissionGranted)
    }
}

// Multiple Runtime Permission handling
fun Context.checkRTPermissions(permissions: Array<String>, completion: PermissionCheckCompletion) {
    var isAllPermissionGranted = CheckPermissionResult.PermissionAsk
    var i = 0
    permissions.forEach { permission ->
        i++
        // If permission is not granted
        if (shouldAskPermission(permission)) {
            isAllPermissionGranted = (this as? Activity)?.let {
                if (ActivityCompat.shouldShowRequestPermissionRationale(it, permission)) {
                    CheckPermissionResult.PermissionPreviouslyDenied
                } else {
                    if (isFirstTimeAskingPermission(permission)) {
                        firstTimeAskingPermission(permission, false)
                        CheckPermissionResult.PermissionAsk
                    } else {
                        CheckPermissionResult.PermissionPermanentlyDenied
                    }
                }
            } ?: kotlin.run {
                CheckPermissionResult.PermissionAsk
            }
        } else {
            CheckPermissionResult.PermissionGranted
        }

        if (i == permissions.size) {
            completion(isAllPermissionGranted)
        }
    }
}

private fun Context.shouldAskPermission(permission: String): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val permissionResult = ActivityCompat.checkSelfPermission(this, permission)
        if (permissionResult == PackageManager.PERMISSION_DENIED) {
            return true
        }
    }
    return false
}

enum class CheckPermissionResult {
    PermissionAsk,
    PermissionPreviouslyDenied,
    PermissionPermanentlyDenied,
    PermissionGranted
}

typealias PermissionCheckCompletion = (CheckPermissionResult) -> Unit
