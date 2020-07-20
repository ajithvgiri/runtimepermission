package com.ajithvgiri.runtimepermissions

import android.content.Context
import android.content.Context.MODE_PRIVATE

fun Context.firstTimeAskingPermission(permission: String, isFirstTime: Boolean) {
    val sharedPreference = getSharedPreferences("Permission", MODE_PRIVATE)
    sharedPreference.edit().putBoolean(permission, isFirstTime).apply()
}

fun Context.isFirstTimeAskingPermission(permission: String): Boolean {
    return getSharedPreferences("Permission", MODE_PRIVATE).getBoolean(permission, true)
}