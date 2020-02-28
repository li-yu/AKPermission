package cn.liyuyu.akpermission

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by liyu on 2020/2/27 13:39.
 */
const val REQUEST_CODE = 2140

class PermissionFragment : Fragment() {

    private var permissionCallback: PermissionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun requestPermissions(
        permissions: Array<out String>,
        callback: PermissionCallback.() -> Unit
    ) {
        permissionCallback = PermissionCallback().apply { callback() }
        val needPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(
                this.context!!,
                it
            ) == PackageManager.PERMISSION_DENIED
        }
        if (needPermissions.isNotEmpty()) {
            requestPermissions(needPermissions.toTypedArray(), REQUEST_CODE)
        } else {
            permissionCallback?.onGranted()
        }
    }

    fun retry(permissions: Array<out String>, callback: PermissionCallback?) {
        permissionCallback = callback
        requestPermissions(permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var allGranted = true
        val neverAskAgainPermissions = mutableListOf<String>()
        val deniedPermissions = mutableListOf<String>()
        val showRationalePermissions = mutableListOf<String>()
        permissions.forEachIndexed { index, permission ->
            if (grantResults[index] == PackageManager.PERMISSION_DENIED) {
                allGranted = false
                if (!shouldShowRequestPermissionRationale(permission)) {
                    neverAskAgainPermissions.add(permission)
                } else {
                    showRationalePermissions.add(permission)
                }
                deniedPermissions.add(permission)
            }
        }
        if (allGranted) {
            permissionCallback?.onGranted()
        } else {
            if (showRationalePermissions.isNotEmpty()) {
                permissionCallback?.onShowRationale(
                    PermissionRationale(
                        this,
                        showRationalePermissions, permissionCallback
                    )
                )
            }

            if (neverAskAgainPermissions.isNotEmpty()) {
                permissionCallback?.onNeverAskAgain(neverAskAgainPermissions)
            }

            if (deniedPermissions.isNotEmpty()) {
                permissionCallback?.onDenied(deniedPermissions)
            }
        }
    }

}


