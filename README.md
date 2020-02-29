# AKPermission

[![](https://jitpack.io/v/li-yu/AKPermission.svg)](https://jitpack.io/#li-yu/AKPermission)

![](showcode.png)
A lightweight Android Runtime Permissions handler in Kotlin.

## Usage

Step 1. Add it in your root build.gradle at the end of repositories:

```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Step 2. Add the dependency:

```
dependencies {
	  implementation 'com.github.li-yu:AKPermission:1.0'
}
```

Step 3. Let the code fly:

You can use `callWithPermissions(...)` in Activity or Fragment just like below:

```kotlin
callWithPermissions(Manifest.permission.RECORD_AUDIO,
    Manifest.permission.WRITE_EXTERNAL_STORAGE) {
    onGranted {
        // 授权成功
    }
    onDenied {
        // 拒绝授权，包含 NeverAskAgain 和 ShowRationale 的情况
    }
    onNeverAskAgain {
        // 拒绝授权并勾选不再询问
    }
    onShowRationale {
        // 拒绝授权，但没有勾选不再询问
        // 此时我们需要对用户解释为什么需要这些权限
        AlertDialog.Builder(this@MainActivity)
            .setMessage("麻烦给个权限呗")
            .setTitle("提醒")
            .setPositiveButton("给") { _, _ ->
                it.retry() // 重新发起授权请求
            }
            .setNegativeButton("不给", null)
            .create()
            .show()
    }
}
```

### Notes

AKPermission supports **androidx** only, and requires at minimum Android 6.0(Api 23).

### License

[Apache License Version 2.0](https://github.com/li-yu/AKPermission/blob/master/LICENSE)
