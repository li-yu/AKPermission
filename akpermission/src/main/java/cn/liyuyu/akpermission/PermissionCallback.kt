package cn.liyuyu.akpermission

/**
 * Created by liyu on 2020/2/27 15:02.
 */
class PermissionCallback {

    private var onGranted: (() -> Unit)? = null
    private var onDenied: ((List<String>) -> Unit)? = null
    private var onShowRationale: ((PermissionRationale) -> Unit)? = null
    private var onNeverAskAgain: ((List<String>) -> Unit)? = null
    private var onRequestCompleted: ((PermissionsResult) -> Unit)? = null
    private var result: PermissionsResult? = null

    fun onGranted(block: () -> Unit) {
        onGranted = block
    }

    fun onDenied(block: (List<String>) -> Unit) {
        onDenied = block
    }

    fun onShowRationale(block: (PermissionRationale) -> Unit) {
        onShowRationale = block
    }

    fun onNeverAskAgain(block: (List<String>) -> Unit) {
        onNeverAskAgain = block
    }

    fun onRequestCompleted(block: (PermissionsResult) -> Unit) {
        onRequestCompleted = block
        result = PermissionsResult()
    }

    internal fun onGranted() {
        onGranted?.invoke()
        result?.isAllGranted = true
    }

    internal fun onDenied(permissions: List<String>) {
        onDenied?.invoke(permissions)
        result?.denied = permissions
    }

    internal fun onShowRationale(permissionRationale: PermissionRationale) {
        onShowRationale?.invoke(permissionRationale)
        result?.rationale = permissionRationale
    }

    internal fun onNeverAskAgain(permissions: List<String>) {
        onNeverAskAgain?.invoke(permissions)
        result?.neverAskAgain = permissions
    }

    internal fun onRequestCompleted() {
        onRequestCompleted?.invoke(result!!)
    }

}
