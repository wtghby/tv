package com.conny.tv.material.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class ApkUtil {
    /**
     * 安装apk包
     *
     * @param context Context 上下文对象
     * @param apkFile File apk文件对象
     */
    public static void installApk(Context context, File apkFile) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(apkFile),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 获取当前版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {

        }
        return versionName;
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int versioncode = 0;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {

        }
        return versioncode;
    }
}
