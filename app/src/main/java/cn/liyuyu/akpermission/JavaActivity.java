package cn.liyuyu.akpermission;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class JavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
    }

    public void recordAudio(View view) {
        AKPermission
                .with(this)
                .permissions(Manifest.permission.RECORD_AUDIO)
                .call(new AKPermission.Callbacks() {
                    @Override
                    public void onDenied(List<String> list) {
                        Toast.makeText(JavaActivity.this, "onDenied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onGranted() {
                        Toast.makeText(JavaActivity.this, "onGranted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    void onShowRationale(PermissionRationale permissionRationale) {
                        new AlertDialog.Builder(JavaActivity.this)
                                .setMessage("Record Audio needs this permissions")
                                .setTitle("Hi")
                                .setPositiveButton("Ok", (dialogInterface, i) -> permissionRationale.retry())
                                .setNegativeButton("No", null)
                                .create()
                                .show();
                    }
                });
    }
}