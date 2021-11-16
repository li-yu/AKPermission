package cn.liyuyu.akpermission.coroutines

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import cn.liyuyu.akpermission.PermissionsResult
import cn.liyuyu.akpermission.callWithPermissions
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by liyu on 2020/3/3 14:41.
 */
suspend fun FragmentActivity.callWithPermissionsResult(vararg permissions: String): PermissionsResult =
    suspendCoroutine {
        runOnUiThread {
            callWithPermissions(*permissions) {
                onRequestCompleted { result ->
                    it.resume(result)
                }
            }
        }
    }

suspend fun Fragment.callWithPermissionsResult(vararg permissions: String): PermissionsResult =
    suspendCoroutine {
        activity?.runOnUiThread {
            callWithPermissions(*permissions) {
                onRequestCompleted { result ->
                    it.resume(result)
                }
            }
        }
    }