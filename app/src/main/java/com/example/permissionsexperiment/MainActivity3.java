package com.example.permissionsexperiment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public final class MainActivity3 extends AppCompatActivity {

    private final ListDirPermissionAction listDirPermissionAction = new ListDirPermissionAction();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(v -> listDirPermissionAction.invoke());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        listDirPermissionAction.onRequestPermissionsResult(requestCode, grantResults);
    }

    private final class ListDirPermissionAction extends PermissionAction {

        private ListDirPermissionAction() {
            super(MainActivity3.this, 0, READ_EXTERNAL_STORAGE);
        }

        @Override
        void run() {
            StorageUtil.listDirStats(MainActivity3.this);
        }

        @Override
        void onShowRationale() {
            Toast.makeText(MainActivity3.this, "This permission is needed", Toast.LENGTH_SHORT).show();
        }

        @Override
        void onPermissionDenied() {
            Toast.makeText(MainActivity3.this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}
