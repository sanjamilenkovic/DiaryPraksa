package com.example.diarypraksa

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


val STORAGE_PERMISSION_CODE = 0x456
val CAMERA_PERMISSION_CODE = 0x457

object PermissionHelper {
    var activity: Activity? = null
    var listener: IPermissionListener? = null
    var permission: String? = null
    var permissionCode: Int = 0

    private var message: String? = null
    private var message2: String? = null


    public fun requestStoragePermission(a: Activity, l: IPermissionListener) {
        requestPermission(
            a,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE,
            a.getString(R.string.permission_storage),
            a.getString(R.string.permission_storage_save_settings),
            l
        )
    }

    public fun requestCameraPermission(a: Activity, l: IPermissionListener) {
        requestPermission(
            a,
            Manifest.permission.CAMERA,
            CAMERA_PERMISSION_CODE,
            a.getString(R.string.camera_storage),
            a.getString(R.string.no_camera_permission),
            l
        )
    }

    public fun requestPermission(
        a: Activity, perm: String = Manifest.permission.READ_EXTERNAL_STORAGE,
        pCode: Int = STORAGE_PERMISSION_CODE,
        msg: String,
        msg2: String,
        l: IPermissionListener
    ) {
        activity = a
        listener = l
        permission = perm
        permissionCode = pCode
        message = msg
        message2 = msg2
        if (ContextCompat.checkSelfPermission(
                a,
                permission!!
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                a,
                arrayOf(permission),
                permissionCode
            )
        } else {
            listener?.permissionAllowed()
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == permissionCode) {
                    listener?.permissionAllowed()
                }
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (requestCode == permissionCode) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            activity!!,
                            permission!!
                        )
                    ) {
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setTitle(getString(R.string.permission_denied))

                        builder.setMessage(message)

                        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
                            ActivityCompat.requestPermissions(
                                activity!!,
                                arrayOf(permission!!),
                                permissionCode
                            )
                        }
                        builder.setNegativeButton(getString(R.string.no)) { _, _ ->
                            listener?.permissionDenied()
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()
                    } else {
                        //Never ask again selected, or device policy prohibits the app from having that permission.
                        //So, disable that feature, or fall back to another situation...
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setTitle(getString(R.string.permission_denied))

                        builder.setMessage(message2)

                        builder.setPositiveButton(getString(R.string.goToSettings)) { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity!!.packageName, null)
                            intent.data = uri
                            activity!!.startActivityForResult(intent, permissionCode)
                        }
                        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
                            listener?.permissionDenied()
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                }
            }
        }
    }

    private fun getString(id: Int): String {
        return activity!!.getString(id)
    }
}