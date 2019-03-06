package com.ajithvgiri.runtimepermissions

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.ContextCompat


object PermissionHandler {

    private fun shouldAskPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
    }

    fun checkPermission(context: Context, permission: String, completion: PermissionCheckCompletion) {
        // If permission is not granted
        if (shouldAskPermission(context, permission)) {
            //If permission denied previously
            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    (context as Activity).shouldShowRequestPermissionRationale(permission)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            ) {
                completion(CheckPermissionResult.PermissionPreviouslyDenied)
            } else {
                // Permission denied or first time requested
                completion(CheckPermissionResult.PermissionAsk)
            }
        } else {
            completion(CheckPermissionResult.PermissionGranted)
        }
    }

    fun checkPermission(context: Context, permission: Array<String>, completion: PermissionCheckCompletion) {
        var isAllPermissionGranted: Boolean
        var i = 0
        permission.forEach {
            i++
            // If permission is not granted
            if (shouldAskPermission(context, it)) {
                //If permission denied previously
                isAllPermissionGranted = if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        (context as Activity).shouldShowRequestPermissionRationale(it)
                    } else {
                        TODO("VERSION.SDK_INT < M")
                    }
                ) {
                    false
                } else {
                    // Permission denied or first time requested
                    false
                }
            } else {
                isAllPermissionGranted = true
            }

            if (i == permission.size) {
                if (isAllPermissionGranted) {
                    completion(CheckPermissionResult.PermissionGranted)
                } else {
                    completion(CheckPermissionResult.PermissionAsk)
                }
            }
        }
    }
}

enum class CheckPermissionResult {
    PermissionAsk,
    PermissionPreviouslyDenied,
    PermissionGranted
}

typealias PermissionCheckCompletion = (CheckPermissionResult) -> Unit
