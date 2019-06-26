package com.example.permissionsexperiment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

final class PermissionManager {

    private final Activity activity;

    private final int requestCode;

    private String permission;

    private Runnable action;

    private Runnable showRationaleAction;

    private Runnable onDeniedAction;

    PermissionManager(Activity activity, int requestCode) {
        this.activity = activity;
        this.requestCode = requestCode;
    }

    PermissionManager setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    PermissionManager setAction(@NonNull Runnable action) {
        this.action = action;
        return this;
    }

    PermissionManager setShowRationaleAction(Runnable showRationaleAction) {
        this.showRationaleAction = showRationaleAction;
        return this;
    }

    PermissionManager setOnDeniedAction(Runnable onDeniedAction) {
        this.onDeniedAction = onDeniedAction;
        return this;
    }

    void invokeAction() {
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            action.run();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            showRationaleAction.run();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }

    void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        if (this.requestCode != requestCode) return;
        if (grantResults.length == 0) return;

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            action.run();
        } else  {
            onDeniedAction.run();
        }
    }
}
