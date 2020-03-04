package cn.liyuyu.akpermission.coroutines

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.liyuyu.akpermission.PermissionsResult
import cn.liyuyu.akpermission.callWithPermissions
import cn.liyuyu.akpermission.getActivity
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by liyu on 2020/3/3 14:41.
 */
suspend fun AppCompatActivity.callWithPermissionsResult(vararg permissions: String): PermissionsResult =
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

suspend fun Context.callWithPermissionsResult(vararg permissions: String): PermissionsResult =
    suspendCoroutine {
        getActivity().runOnUiThread {
            callWithPermissions(*permissions) {
                onRequestCompleted { result ->
                    it.resume(result)
                }
            }
        }
    }