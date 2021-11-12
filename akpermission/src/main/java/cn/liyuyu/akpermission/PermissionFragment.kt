package cn.liyuyu.akpermission

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by liyu on 2020/2/27 13:39.
 */
class PermissionFragment : Fragment() {

    private var permissionCallback: PermissionCallback? = null
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                var allGranted = true
                val neverAskAgainPermissions = mutableListOf<String>()
                val deniedPermissions = mutableListOf<String>()
                val showRationalePermissions = mutableListOf<String>()

                result.forEach {
                    if (!it.value) {
                        allGranted = false
                        if (!shouldShowRequestPermissionRationale(it.key)) {
                            neverAskAgainPermissions.add(it.key)
                        } else {
                            showRationalePermissions.add(it.key)
                        }
                        deniedPermissions.add(it.key)
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
                permissionCallback?.onRequestCompleted()
            }
    }

    fun requestPermissions(
        permissions: Array<out String>,
        callback: PermissionCallback.() -> Unit
    ) {
        permissionCallback = PermissionCallback().apply { callback() }
        val needPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                it
            ) == PackageManager.PERMISSION_DENIED
        }
        if (needPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(needPermissions.toTypedArray())
        } else {
            permissionCallback?.onGranted()
            permissionCallback?.onRequestCompleted()
        }
    }

    fun retry(permissions: Array<String>, callback: PermissionCallback?) {
        permissionCallback = callback
        requestPermissionLauncher.launch(permissions)
    }
}


