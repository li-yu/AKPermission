package cn.liyuyu.akpermission;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

/**
 * yes,just for java.
 * Created by liyu on 2021/11/16 15:45.
 */
public class AKPermission {
    private FragmentActivity hostActivity;
    private String[] permissions;

    public AKPermission(FragmentActivity activity) {
        this.hostActivity = activity;
    }

    public static AKPermission with(FragmentActivity activity) {
        return new AKPermission(activity);
    }

    public static AKPermission with(Fragment fragment) {
        return new AKPermission(fragment.requireActivity());
    }

    public AKPermission permissions(String... permissions) {
        if (this.hostActivity == null) {
            throw new IllegalStateException("The 'AKPermission.with()' method should be called first.");
        }
        this.permissions = permissions;
        return this;
    }

    public void call(Callbacks callbacks) {
        if (this.hostActivity == null || permissions == null) {
            throw new IllegalStateException("The hostActivity or permissions cannot be null.");
        }
        FucExtKt.callWithPermissions(this.hostActivity, this.permissions, callback -> {
            callback.onDenied(strings -> {
                callbacks.onDenied(strings);
                return null;
            });
            callback.onGranted(() -> {
                callbacks.onGranted();
                return null;
            });
            callback.onNeverAskAgain(strings -> {
                callbacks.onNeverAskAgain(strings);
                return null;
            });
            callback.onShowRationale(permissionRationale -> {
                callbacks.onShowRationale(permissionRationale);
                return null;
            });
            return null;
        });
    }

    public abstract static class Callbacks {

        abstract void onGranted();

        void onDenied(List<String> permissions) {
        }

        void onNeverAskAgain(List<String> permissions) {
        }

        void onShowRationale(PermissionRationale permissionRationale) {
        }

    }
}
