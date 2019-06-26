package com.example.permissionsexperiment;

import android.content.Context;
import android.util.Log;

import java.io.File;

final class StorageUtil {

    static void listDirStats(Context context) {
        for (File externalFilesDir : context.getExternalFilesDirs(null)) {
            long totalSpace = externalFilesDir.getTotalSpace();
            long freeSpace = externalFilesDir.getFreeSpace();
            long usedSpace = totalSpace - freeSpace;
            String freeFormat = formatFilesize(freeSpace);
            String totalFormat = formatFilesize(totalSpace);
            String usedFormat = formatFilesize(usedSpace);
            String output = String.format("total: %s, used: %s, free: %s", totalFormat, usedFormat, freeFormat);
            Log.w("RABBIT", output);
        }
    }

    private static String formatFilesize(long bytes) {
        String suffix = "B";
        double value = bytes;

        if (value > 1000) {
            value /= 1000;
            suffix = "kB";
        }

        if (value > 1000) {
            value /= 1000;
            suffix = "MB";
        }

        if (value > 1000) {
            value /= 1000;
            suffix = "GB";
        }

        return String.format("%s %s", value, suffix);
    }
}
