package cn.liyuyu.akpermission

/**
 * Created by liyu on 2020/2/27 15:48.
 */
class PermissionRationale(
    private val permissionFragment: PermissionFragment,
    val permissions: List<String>,
    private val callback: PermissionCallback?
) {

    fun retry() {
        permissionFragment.retry(permissions.toTypedArray(), callback)
    }

}