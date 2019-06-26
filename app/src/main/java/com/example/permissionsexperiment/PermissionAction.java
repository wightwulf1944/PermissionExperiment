package com.example.permissionsexperiment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

abstract class PermissionAction {

    private final Activity activity;

    private final int requestCode;

    private final String permission;

    PermissionAction(Activity activity, int requestCode, String permission) {
        this.activity = activity;
        this.requestCode = requestCode;
        this.permission = permission;
    }

    void invoke() {
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            run();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            onShowRationale();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        if (this.requestCode != requestCode) return;
        if (grantResults.length == 0) return;

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            run();
        } else {
            onPermissionDenied();
        }
    }

    abstract void run();

    abstract void onShowRationale();

    abstract void onPermissionDenied();
}
