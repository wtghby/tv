package com.conny.tv.material.utils;

import android.util.Log;

import com.conny.tv.api.ApiManager;

/**
 * Created by Administrator on 2017/7/15.
 */

public class LogUtils {
    private LogUtils() {
    }

    public static void i(String tag, String msg) {
        if (!ApiManager.RELEASE) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (!ApiManager.RELEASE) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (!ApiManager.RELEASE) {
            Log.e(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (!ApiManager.RELEASE) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (!ApiManager.RELEASE) {
            Log.w(tag, msg);
        }
    }
}
