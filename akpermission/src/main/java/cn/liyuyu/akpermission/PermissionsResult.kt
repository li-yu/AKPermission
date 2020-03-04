package cn.liyuyu.akpermission

/**
 * Created by liyu on 2020/3/3 15:31.
 */
data class PermissionsResult(
    var isAllGranted: Boolean = false,
    var rationale: PermissionRationale? = null,
    var denied: List<String>? = null,
    var neverAskAgain: List<String>? = null
)