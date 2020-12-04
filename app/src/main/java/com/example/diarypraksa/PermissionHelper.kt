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
import androidx.core.content.ContextCompat.checkSelfPermission

val STORAGE_PERMISSION_CODE = 0x456

object PermissionHelper {
    var activity: Activity? = null
    var listener: IPermissionListener? = null
    var permission: String? = null
    var permissionCode : Int = 0

    private var message: String? = null
    private var message2: String? = null

    public fun requestStoragePermission(a: Activity, l: IPermissionListener) {
        requestPermission(
            a,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            STORAGE_PERMISSION_CODE,
            "Without this permission the app is unable to allow you choose images from your library",
            "The app is unable to allow you choose images from your library",
            l
        )
    }

    public fun requestPermission(act: Activity,
                                 p: String = Manifest.permission.READ_EXTERNAL_STORAGE,
                                 code : Int = STORAGE_PERMISSION_CODE,
                                 msg: String,
                                 msg2: String,
                                 ipListener: IPermissionListener)
    {
        activity = act
        listener = ipListener
        permission = p
        permissionCode = code
        message = msg
        message2 = msg2

        if (ContextCompat.checkSelfPermission(act, permission!!) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(act, arrayOf(permission), permissionCode)
        }
        else
        {
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
                        builder.setTitle("Permission denied")

                        builder.setMessage(message)

                        builder.setPositiveButton("Allow") { _, _ ->
                            ActivityCompat.requestPermissions(
                                activity!!,
                                arrayOf(permission!!),
                                permissionCode
                            )
                        }
                        builder.setNegativeButton("Deny") { _, _ ->
                            listener?.permissionDenied()
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()
                    } else {
                        //Never ask again selected, or device policy prohibits the app from having that permission.
                        //So, disable that feature, or fall back to another situation...
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setTitle("Permission denied")

                        builder.setMessage(message2)

                        builder.setPositiveButton("Go to settings") { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity!!.packageName, null)
                            intent.data = uri
                            activity!!.startActivityForResult(intent, permissionCode)
                        }
                        builder.setNegativeButton("Cancel") { _, _ ->
                            listener?.permissionDenied()
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                }
            }
        }
    }
}



interface IPermissionListener {
    fun permissionAllowed()
    fun permissionDenied()
}