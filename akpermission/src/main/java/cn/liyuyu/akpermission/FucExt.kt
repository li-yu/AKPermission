package cn.liyuyu.akpermission

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by liyu on 2020/2/27 16:38.
 */
const val TAG = "PERMISSION_FRAGMENT"

fun AppCompatActivity.callWithPermissions(
    vararg permissions: String,
    callback: PermissionCallback.() -> Unit
) {
    getPermissionFragment(supportFragmentManager).requestPermissions(permissions, callback)
}

fun Fragment.callWithPermissions(
    vararg permissions: String,
    callback: PermissionCallback.() -> Unit
) {
    getPermissionFragment(childFragmentManager).requestPermissions(permissions, callback)
}

@Synchronized
private fun getPermissionFragment(fragmentManager: FragmentManager): PermissionFragment {
    var fragment = fragmentManager.findFragmentByTag(TAG)
    if (fragment == null) {
        fragment = PermissionFragment().also {
            fragmentManager.beginTransaction().add(it, TAG).commitNow()
        }
    }
    return fragment as PermissionFragment
}