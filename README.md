# RunTime Permissions

This Android library provides Kotlin extension functions that make run time permission handling easier and more concise. These extensions provide same implementation for permission handling in both Activities and Fragments.

### Usage
Most of the system permission are defined in *[AppPermission.kt](https://github.com/ajithvgiri/runtimepermission/blob/master/library/src/main/java/com/ajithvgiri/runtimepermissions/AppPermissions.kt)* class.

##### Single Permission

``` kotlin

    companion object {
        private const val PERMISSION_REQUEST_CAMERA = 100
    }

    // Single Permission
    private fun askPermission(permission: String = PERMISSION_CAMERA, requestCode: Int = PERMISSION_REQUEST_CAMERA) {
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
                    // Show action for navigating user to settings 
                }
            }
        }
    }


    //Permission Request Result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
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
        }
    }
    
    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



```

##### Multiple Permissions

``` kotlin

    companion object {
        private const val PERMISSION_REQUEST_CONTACT = 101
        private const val PERMISSION_REQUEST_STORAGE = 102
        private const val PERMISSION_REQUEST_CAMERA_STORAGE = 103
    }


    // Multiple Permissions
    buttonContactPermission.setOnClickListener {
        val contactReadWritePermissions =
            arrayOf(PERMISSION_READ_CONTACTS, PERMISSION_WRITE_CONTACTS)
        askPermissions(contactReadWritePermissions, PERMISSION_REQUEST_CONTACT)
    }

    buttonStoragePermission.setOnClickListener {
        val externalStorageReadWritePermissions = arrayOf(PERMISSION_READ_EXTERNAL_STORAGE, PERMISSION_WRITE_EXTERNAL_STORAGE)
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

    // Multiple Permissions
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
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
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



```



## Licence
```
MIT License

Copyright (c) 2020 Ajith v Giri

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```