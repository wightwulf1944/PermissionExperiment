package com.example.permissionsexperiment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

final class PermissionFlow {

    private final Activity activity;

    private final int requestCode;

    private final String permission;

    private final Runnable action;

    private final Runnable showRationaleAction;

    private final Runnable onDeniedAction;

    private PermissionFlow(Builder builder) {
        activity = builder.activity;
        requestCode = builder.requestCode;
        permission = builder.permission;
        action = builder.action;
        showRationaleAction = builder.showRationaleAction;
        onDeniedAction = builder.onDeniedAction;
    }

    void start() {
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
        } else {
            onDeniedAction.run();
        }
    }

    static final class Builder {

        private final Activity activity;

        private final int requestCode;

        private String permission;

        private Runnable action;

        private Runnable showRationaleAction;

        private Runnable onDeniedAction;

        Builder(Activity activity, @IntRange(from = 0) int requestCode) {
            this.activity = activity;
            this.requestCode = requestCode;
        }

        Builder setPermission(String permission) {
            this.permission = permission;
            return this;
        }

        Builder setAction(Runnable action) {
            this.action = action;
            return this;
        }

        Builder setShowRationaleAction(Runnable showRationaleAction) {
            this.showRationaleAction = showRationaleAction;
            return this;
        }

        Builder setOnDeniedAction(Runnable onDeniedAction) {
            this.onDeniedAction = onDeniedAction;
            return this;
        }

        PermissionFlow build() {
            return new PermissionFlow(this);
        }
    }
}
