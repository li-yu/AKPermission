package cn.liyuyu.akpermission

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


/**
 * Created by liyu on 2020/2/27 16:38.
 */
internal const val TAG = "PERMISSION_FRAGMENT"

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

fun View.callWithPermissions(
    vararg permissions: String,
    callback: PermissionCallback.() -> Unit
) {
    getPermissionFragment(getHostActivity().supportFragmentManager).requestPermissions(
        permissions,
        callback
    )
}

fun Context.callWithPermissions(
    vararg permissions: String,
    callback: PermissionCallback.() -> Unit
) {
    getPermissionFragment(getActivity().supportFragmentManager).requestPermissions(
        permissions,
        callback
    )
}

@Synchronized
private fun getPermissionFragment(fragmentManager: FragmentManager): PermissionFragment {
    var fragment = fragmentManager.findFragmentByTag(TAG)
    if (fragment == null) {
        fragment = PermissionFragment().also {
            fragmentManager.beginTransaction().add(it, TAG).commitNow()
            fragmentManager.executePendingTransactions()
        }
    }
    return fragment as PermissionFragment
}

private fun View.getHostActivity(): AppCompatActivity {
    var tempContext = context
    while (tempContext is ContextWrapper) {
        if (tempContext is AppCompatActivity) {
            return tempContext
        }
        tempContext = tempContext.baseContext
    }
    throw IllegalStateException("Can not get AppCompatActivity from $this, you should use callWithPermissions(...) by context based on AppCompatActivity.")
}

private fun Context.getActivity(): AppCompatActivity {
    var tempContext = this
    while (tempContext is ContextWrapper) {
        if (tempContext is AppCompatActivity) {
            return tempContext
        }
        tempContext = tempContext.baseContext
    }
    throw IllegalStateException("Can not get AppCompatActivity from $this, you should use callWithPermissions(...) by context based on AppCompatActivity.")
}