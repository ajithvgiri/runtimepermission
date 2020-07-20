package com.ajithvgiri.permissionhandler

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ajithvgiri.runtimepermissions.AppPermissions.Companion.PERMISSION_CAMERA
import com.ajithvgiri.runtimepermissions.AppPermissions.Companion.PERMISSION_READ_CONTACTS
import com.ajithvgiri.runtimepermissions.AppPermissions.Companion.PERMISSION_READ_EXTERNAL_STORAGE
import com.ajithvgiri.runtimepermissions.AppPermissions.Companion.PERMISSION_WRITE_CONTACTS
import com.ajithvgiri.runtimepermissions.AppPermissions.Companion.PERMISSION_WRITE_EXTERNAL_STORAGE
import com.ajithvgiri.runtimepermissions.CheckPermissionResult
import com.ajithvgiri.runtimepermissions.checkRTPermission
import com.ajithvgiri.runtimepermissions.checkRTPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CAMERA = 100
        private const val PERMISSION_REQUEST_CONTACT = 101
        private const val PERMISSION_REQUEST_STORAGE = 102
        private const val PERMISSION_REQUEST_CAMERA_STORAGE = 103
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Single Permission
        buttonCameraPermission.setOnClickListener {
            askPermission(PERMISSION_CAMERA, PERMISSION_REQUEST_CAMERA)
        }

        // Multiple Permissions
        buttonContactPermission.setOnClickListener {
            val contactReadWritePermissions =
                arrayOf(PERMISSION_READ_CONTACTS, PERMISSION_WRITE_CONTACTS)
            askPermissions(contactReadWritePermissions, PERMISSION_REQUEST_CONTACT)
        }

        buttonStoragePermission.setOnClickListener {
            val externalStorageReadWritePermissions =
                arrayOf(PERMISSION_READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE)
            askPermissions(externalStorageReadWritePermissions, PERMISSION_REQUEST_STORAGE)
        }

        buttonCameraStoragePermission.setOnClickListener {
            val cameraAndExternalStoragePermissions = arrayOf(
                PERMISSION_CAMERA,
                PERMISSION_READ_EXTERNAL_STORAGE,
                PERMISSION_WRITE_EXTERNAL_STORAGE
            )
            askPermissions(cameraAndExternalStoragePermissions, PERMISSION_REQUEST_CAMERA_STORAGE)
        }

    }

    // Single Permission
    private fun askPermission(permission: String, requestCode: Int) {
        checkRTPermission(permission) { result ->
            when (result) {
                CheckPermissionResult.PermissionGranted -> {
                    showMessage("Permission Granted")
                }
                CheckPermissionResult.PermissionAsk -> {
                    ActivityCompat.requestPermissions(this, arrayOf(PERMISSION_CAMERA), requestCode)
                }
                CheckPermissionResult.PermissionPreviouslyDenied -> {
                    // Show some message for user why this permission is needed
                    ActivityCompat.requestPermissions(this, arrayOf(PERMISSION_CAMERA), requestCode)
                }
                CheckPermissionResult.PermissionPermanentlyDenied -> {
                    showSnackBar()
                }
            }
        }
    }

    // Multiple Permission
    private fun askPermissions(permissions: Array<String>, requestCode: Int) {
        checkRTPermissions(permissions) { result ->
            when (result) {
                CheckPermissionResult.PermissionGranted -> {
                    showMessage("Permission Granted")
                }
                CheckPermissionResult.PermissionAsk -> {
                    ActivityCompat.requestPermissions(this, permissions, requestCode)
                }
                CheckPermissionResult.PermissionPreviouslyDenied -> {
                    // Show some message for user why this permission is needed
                    ActivityCompat.requestPermissions(this, permissions, requestCode)
                }
                CheckPermissionResult.PermissionPermanentlyDenied -> {
                    showSnackBar()
                }
            }
        }
    }


    //Permission Request Result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CAMERA -> if (grantResults.isNotEmpty()) {
                val cameraPermissionAccepted =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (cameraPermissionAccepted) {
                    showMessage("Camera Permission Enabled")
                } else {
                    showMessage("Camera Permission Denied")
                }
            }

            PERMISSION_REQUEST_CONTACT -> if (grantResults.isNotEmpty()) {
                val readContactPermissionAccepted =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeContactPermissionAccepted =
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readContactPermissionAccepted && writeContactPermissionAccepted) {
                    showMessage("Contact Permission Enabled")
                } else {
                    showMessage("Contact Permission Denied")
                }
            }
            PERMISSION_REQUEST_STORAGE -> if (grantResults.isNotEmpty()) {
                val readStoragePermissionAccepted =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeStoragePermissionAccepted =
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readStoragePermissionAccepted && writeStoragePermissionAccepted) {
                    showMessage("Storage Permission Enabled")
                } else {
                    showMessage("Storage Permission Denied")
                }
            }

            PERMISSION_REQUEST_CAMERA_STORAGE -> if (grantResults.isNotEmpty()) {
                val readStoragePermissionAccepted =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeStoragePermissionAccepted =
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                val cameraPermissionAccepted =
                    grantResults[2] == PackageManager.PERMISSION_GRANTED
                if (readStoragePermissionAccepted && writeStoragePermissionAccepted && cameraPermissionAccepted) {
                    showMessage("Camera & Storage Permission Enabled")
                } else {
                    showMessage("Camera & Storage Permission Denied")
                }
            }

        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // SnackBar with action for navigating user to Settings page
    private fun showSnackBar() {
        val parentLayout = findViewById<View>(android.R.id.content)
        Snackbar.make(
            parentLayout,
            "Permission is permanently denied, enable from settings",
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.settings)) {
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                })
            }
            .show()
    }
}
