package cn.liyuyu.akpermission

/**
 * Created by liyu on 2020/2/27 15:02.
 */
class PermissionCallback {

    private var onGranted: () -> Unit = {}
    private var onDenied: (permissions: List<String>) -> Unit = {}
    private var onShowRationale: (permissionRequest: PermissionRationale) -> Unit = {}
    private var onNeverAskAgain: (permissions: List<String>) -> Unit = {}

    fun onGranted(func: () -> Unit) {
        onGranted = func
    }

    fun onDenied(func: (permissions: List<String>) -> Unit) {
        onDenied = func
    }

    fun onShowRationale(func: (permissionRequest: PermissionRationale) -> Unit) {
        onShowRationale = func
    }

    fun onNeverAskAgain(func: (permissions: List<String>) -> Unit) {
        onNeverAskAgain = func
    }

    internal fun onGranted() {
        onGranted.invoke()
    }

    internal fun onDenied(permissions: List<String>) {
        onDenied.invoke(permissions)
    }

    internal fun onShowRationale(permissionRequest: PermissionRationale) {
        onShowRationale.invoke(permissionRequest)
    }

    internal fun onNeverAskAgain(permissions: List<String>) {
        onNeverAskAgain.invoke(permissions)
    }

}
