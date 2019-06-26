package com.example.permissionsexperiment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public final class MainActivity5 extends AppCompatActivity {

    private final PermissionFlow permissionFlow = new PermissionFlow.Builder(this, 0)
            .setPermission(READ_EXTERNAL_STORAGE)
            .setAction(() -> StorageUtil.listDirStats(this))
            .setShowRationaleAction(() -> Toast.makeText(this, "This permission is needed", Toast.LENGTH_SHORT).show())
            .setOnDeniedAction(() -> Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show())
            .build();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(v -> permissionFlow.start());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionFlow.onRequestPermissionsResult(requestCode, grantResults);
    }
}
