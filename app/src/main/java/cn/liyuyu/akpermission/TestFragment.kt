package cn.liyuyu.akpermission

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import cn.liyuyu.akpermission.coroutines.callWithPermissionsResult
import kotlinx.android.synthetic.main.fragment_test.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Created by liyu on 2020/2/28 15:44.
 */
class TestFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentContent = inflater.inflate(R.layout.fragment_test, container, false);
        fragmentContent.btn3.setOnClickListener {
            readPhone()
        }
        fragmentContent.btn4.setOnClickListener {
            getLocation()
        }
        fragmentContent.btn5.setOnClickListener {
            getLocationInBackground()
        }
        return fragmentContent
    }

    private fun readPhone() = callWithPermissions(Manifest.permission.READ_PHONE_STATE) {
        onGranted {
            Log.d(LOG_TAG, "onGranted")
            Toast.makeText(activity, "onGranted", Toast.LENGTH_SHORT).show()
        }
        onDenied {
            Log.d(LOG_TAG, "onDenied $it")
        }
        onNeverAskAgain {
            Log.d(LOG_TAG, "onNeverAskAgain $it")
        }
        onShowRationale {
            Log.d(LOG_TAG, "onShowRationale ${it.permissions}")
            AlertDialog.Builder(activity!!)
                .setMessage("麻烦给个权限呗")
                .setTitle("提醒")
                .setPositiveButton("给") { _, _ ->
                    it.retry()
                }
                .setNegativeButton("不给", null)
                .create()
                .show()
        }
    }

    private fun getLocation() = callWithPermissions(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) {
        onGranted {
            Log.d(LOG_TAG, "onGranted")
            Toast.makeText(activity, "onGranted", Toast.LENGTH_SHORT).show()
        }
        onDenied {
            Log.d(LOG_TAG, "onDenied $it")
        }
        onNeverAskAgain {
            Log.d(LOG_TAG, "onNeverAskAgain $it")
        }
        onShowRationale {
            Log.d(LOG_TAG, "onShowRationale ${it.permissions}")
            AlertDialog.Builder(activity!!)
                .setMessage("麻烦给个权限呗")
                .setTitle("提醒")
                .setPositiveButton("给") { _, _ ->
                    it.retry()
                }
                .setNegativeButton("不给", null)
                .create()
                .show()
        }
    }

    private fun getLocationInBackground() {
        GlobalScope.launch {
            withContext(Dispatchers.Default) {
                val result = callWithPermissionsResult(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                if (result.isAllGranted) {
                    // ...
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
                    }
                }
                Log.d(LOG_TAG, result.toString())
            }
        }
    }
}