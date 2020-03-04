package cn.liyuyu.akpermission

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import cn.liyuyu.akpermission.coroutines.callWithPermissionsResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val LOG_TAG = "AK_MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            recordAudio()
        }

        btn2.setOnClickListener {
            takePhoto()
        }
        btn3.setOnClickListener {
            downloadFile()
        }
    }

    private fun downloadFile() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                var result = callWithPermissionsResult(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                Log.d(LOG_TAG, result.toString())
                if (result.isAllGranted) {
                    // ...
                }
            }
        }
    }

    private fun recordAudio() = callWithPermissions(Manifest.permission.RECORD_AUDIO) {
        onGranted {
            Log.d(LOG_TAG, "onGranted")
            Toast.makeText(this@MainActivity, "onGranted", Toast.LENGTH_SHORT).show()
        }
        onDenied {
            Log.d(LOG_TAG, "onDenied $it")
        }
        onNeverAskAgain {
            Log.d(LOG_TAG, "onNeverAskAgain $it")
        }
        onShowRationale {
            Log.d(LOG_TAG, "onShowRationale ${it.permissions}")
            AlertDialog.Builder(this@MainActivity)
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

    private fun takePhoto() = callWithPermissions(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) {
        onGranted {
            Log.d(LOG_TAG, "onGranted")
            Toast.makeText(this@MainActivity, "onGranted", Toast.LENGTH_SHORT).show()
        }
        onDenied {
            Log.d(LOG_TAG, "onDenied $it")
        }
        onNeverAskAgain {
            Log.d(LOG_TAG, "onNeverAskAgain $it")
        }
        onShowRationale {
            Log.d(LOG_TAG, "onShowRationale ${it.permissions}")
            AlertDialog.Builder(this@MainActivity)
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
}
