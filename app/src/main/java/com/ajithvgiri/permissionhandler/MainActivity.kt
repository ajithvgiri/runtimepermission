package com.ajithvgiri.permissionhandler

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.ajithvgiri.runtimepermissions.CheckPermissionResult
import com.ajithvgiri.runtimepermissions.PermissionHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CONTACT = 100
        private const val PERMISSION_REQUEST_STORAGE = 101
        private val PERMISSION_CONTACTS = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
        private val PERMISSION_STORAGE =
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttonContactPermission.setOnClickListener {
            askPermissions()
        }
    }

    private fun askPermissions() {
        PermissionHandler.checkPermission(this, PERMISSION_CONTACTS) { result ->
            when (result) {
                CheckPermissionResult.PermissionGranted -> {

                }
                CheckPermissionResult.PermissionAsk -> {
                    ActivityCompat.requestPermissions(this, PERMISSION_CONTACTS, PERMISSION_REQUEST_CONTACT)
                }
                CheckPermissionResult.PermissionPreviouslyDenied -> {
                    ActivityCompat.requestPermissions(this, PERMISSION_CONTACTS, PERMISSION_REQUEST_CONTACT)
                }
            }
        }
    }


    //Permission Request Result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CONTACT -> if (grantResults.isNotEmpty()) {
                val readContactPermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeContactPermissionAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readContactPermissionAccepted && writeContactPermissionAccepted) {
                    showMessage("Contact Permission Enabled")
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) ||
                            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)
                        ) {
                            return
                        }
                    }
                    showMessage("Contact Permission Denied")
                }
            }
            PERMISSION_REQUEST_STORAGE -> if (grantResults.isNotEmpty()) {
                val readStoragePermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeStoragePermissionAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readStoragePermissionAccepted && writeStoragePermissionAccepted) {
                    showMessage("Storage Permission Enabled")
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ) {
                            return
                        }
                    }
                    showMessage("Storage Permission Denied")
                }
            }

        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
