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