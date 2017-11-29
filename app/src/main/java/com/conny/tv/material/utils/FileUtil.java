package com.conny.tv.material.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Desc:
 * Created by zhanghui on 2017/11/29.
 */

public class FileUtil {
    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "igoo/download/";

    public static void saveFile(InputStream is) {
        File file = new File(DOWNLOAD_PATH, "igoo.apk");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static boolean hasApk(Context context, int versionCode) {
        File file = new File(DOWNLOAD_PATH, "igoo.apk");
        if (file.exists()) {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageArchiveInfo(file.getPath(), PackageManager.GET_ACTIVITIES);
            if (info != null) {
                return info.versionCode >= versionCode;
            }
        }
        return false;
    }

    public static void installApk(Context context) {
        File apkfile = new File(DOWNLOAD_PATH, "igoo.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
