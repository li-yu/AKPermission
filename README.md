# AKPermission

[![](https://jitpack.io/v/li-yu/AKPermission.svg)](https://jitpack.io/#li-yu/AKPermission)

![](showcode.png)
A lightweight Android Runtime Permissions handler in Kotlin, also with coroutines.

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
	implementation 'com.github.li-yu:AKPermission:1.2.3' // both core and coroutines ext.
	// implementation 'com.github.li-yu.AKPermission:akpermission:1.2.3' // core logic,not contains coroutines ext.
	// implementation 'com.github.li-yu.AKPermission:akpermission-coroutines:1.2.3' // coroutines ext only.
}
```

Step 3. Coding:

You can use `callWithPermissions(...)` in **Activity**, **Fragment** just like below:

```kotlin
callWithPermissions(Manifest.permission.RECORD_AUDIO,
    Manifest.permission.WRITE_EXTERNAL_STORAGE) {
    onGranted {
        // all granted
    }
    onDenied {
        // denied, contains NeverAskAgain and ShowRationale permissions
    }
    onNeverAskAgain {
        // denied, and user checked NeverAskAgain button
    }
    onShowRationale {
        // denied, but not checked NeverAskAgain button
        // we need to explain to the user why these permissions are needed
        AlertDialog.Builder(this@MainActivity)
			.setMessage("Record Audio needs this permissions")
			.setTitle("Hi")
			.setPositiveButton("Yes") { _, _ ->
				it.retry()
			}
			.setNegativeButton("No", null)
			.create()
			.show()
    }
}
```

### Kotlin Coroutine

Use `callWithPermissionsResult(...)` in coroutines, for example:

```kotlin
suspend fun downloadFile() = withContext(Dispatchers.IO) {
    val result = callWithPermissionsResult(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (result.isAllGranted) {
        startDownload()
    }
}
```

### In Java

```kotlin
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
```



### Notes

AKPermission requires **AndroidX** and at minimum Android 4.0.3(**API 15**).

### License

[Apache License Version 2.0](https://github.com/li-yu/AKPermission/blob/master/LICENSE)
