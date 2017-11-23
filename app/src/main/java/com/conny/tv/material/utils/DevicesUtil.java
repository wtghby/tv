package com.conny.tv.material.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevicesUtil {

    public static boolean isMX3 = false;

    static {
        String model = Build.MODEL.trim().toLowerCase();
        if (model.equalsIgnoreCase("m353")
                || Build.DEVICE.equalsIgnoreCase("mx3")) {
            isMX3 = true;
        }
    }

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (null == imei)
            return "";
        else {
            boolean isNum = imei.matches("[0-9]+");
            return isNum ? imei : null;
        }
    }

    // sim卡唯一标识
    public static String getSIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String simei = tm.getSubscriberId();
        if (null == simei)
            return "";
        else
            return simei;
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String num = tm.getLine1Number();
        if (null == num)
            return "";
        else
            return num;
    }

    public static String getPhoneProvider(Context context) {
        String simei = getSIMEI(context);
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (simei.startsWith("4600001")) {
            return "中国联通";
        } else if (simei.startsWith("4600002")) {
            return "中国移动";
        } else if (simei.startsWith("4600003")) {
            return "中国电信";
        } else {
            return "";
        }
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneType() {
        return Build.MODEL;
    }

    /**
     * 获取系统版本
     *
     * @return
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取系统版本Level
     *
     * @return
     */
    public static int getSystemVersionLevel() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 手机品牌
     *
     * @return
     */
    public static String getPhoneName() {
        return Build.BRAND;
    }

    /**
     * 获取当前系统可用内存
     *
     * @param ctx
     * @return
     */
    public static String getAvailMemory(Context ctx) {
        ActivityManager am = (ActivityManager) ctx
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        return Formatter.formatFileSize(ctx, mi.availMem);// 将获取的内存大小规格化
    }

    /**
     * 判断是否是平板
     *
     * @param ctx
     * @return
     */
    public static boolean isPadType(Context ctx) {
        TelephonyManager telMg = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (telMg.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据view显示输入法
     *
     * @param ctx
     * @param view
     */
    public static void showIMM(Context ctx, View view) {
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     *
     * @param ctx
     * @param token
     */
    public static void hideIMM(Context ctx, IBinder token) {
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(token,
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    // 获取状态栏高度
    public static int getStatusHeight(Activity activity) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                        .getField("status_bar_height").get(localObject)
                        .toString());
                statusHeight = activity.getResources()
                        .getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 打电话
     *
     * @param context
     * @param phone   传入的电话号码
     */
    public static void call(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    // 判断当前设备是否是模拟器。如果返回TRUE，则当前是模拟器，不是返回FALSE
    public static boolean isEmulator(Context context) {
        if (context == null) {
            return false;
        }
        try {
            return (Build.MODEL.equals("sdk"))
                    || (Build.MODEL.equals("google_sdk"));
        } catch (Exception ioe) {

        }
        return false;
    }

    /**
     * 验证手机号是否合法
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneNum(String phone) {
        Pattern p = Pattern.compile("^1[3,4,5,7,8]\\d{9}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 验证手机号是否支持短信验证 180,181不支持
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneNumCanAuth(String phone) {
        Pattern p = Pattern.compile("^1[8][0,1]\\d{8}$");
        Matcher m = p.matcher(phone);
        return !m.matches();
    }

    /**
     * 检测普通电话号码是否符合规则（仅检测位数）
     */
    public static boolean checkNormalPhone(String phone) {
        int length = phone.length();
        if (length == 7 || length == 8 || length == 10 || length == 11
                || length == 12) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkUserPhone(String phone) {
        int length = phone.length();
        if (length == 11) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检验邮箱地址是否正确
     */
    public static boolean checkEmail(String email) {
        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 检验QQ号码是否合法
     */
    public static boolean checkQQ(String qq) {
        Pattern p = Pattern.compile("^[1-9]\\d{4,10}$");
        Matcher m = p.matcher(qq);
        return m.matches();
    }

    /**
     * 电话号码转换为十六进制字符串
     *
     * @param numStr
     * @return
     */
    public static String numToHexString(String numStr) {
        String hexString = "";
        try {
            hexString = Long.toHexString(Long.valueOf(numStr));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    public static void startPollingService(Context ctx, Class<?> cls, int timeMillis, String action) {
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ctx, cls);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(ctx, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeMillis, pendingIntent);
    }
}
