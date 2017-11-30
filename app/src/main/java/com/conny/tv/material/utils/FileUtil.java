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
import java.text.DecimalFormat;

/**
 * Desc:
 * Created by zhanghui on 2017/11/29.
 */

public class FileUtil {
    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory() + "/igoo/download/";

    public static void saveFile(InputStream is) {
        File dir = new File(DOWNLOAD_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir.getPath(), "igoo.apk");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static String formatDuration(long duration) {
        long second = duration / 1000;

        int hour = (int) (second / 3600);

        int min = 0;
        int sec = 0;
        long ms = second % 3600;
        if (ms >= 60) {
            min = (int) (ms / 60);
            sec = (int) (ms % 60);
        } else {
            sec = (int) ms;
        }

        StringBuilder time = new StringBuilder();

        if (hour > 0) {
            time.append(hour);
        } else {
            time.append("00");
        }
        time.append(':');

        if (min > 0) {
            if (min < 10) {
                time.append('0');
            }
            time.append(min);
        } else {
            time.append("00");
        }
        time.append(':');

        if (sec < 10) {
            time.append('0');
        }
        time.append(sec);
        return time.toString();
    }
}
