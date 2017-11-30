package com.conny.tv.api;

import android.content.Context;

/**
 * Desc:
 * Created by zhanghui on 2017/11/9.
 */

public class ApiManager {

    public static final int DEVELOP = 0;
    public static final int TEST = 1;
    public static final int CLUSTER = 2;

    //正式发版时需要置为true
    public static final boolean RELEASE = false;

    private static final String API_DEVELOP_SERVER = "http://192.168.0.101:8080/mob/api/";
    private static final String API_TEST_SERVER = "";
    private static final String API_CLUSTER_SERVER = "http://47.93.10.147:8080/tv/mob/api/";

    public static String SERVER = API_DEVELOP_SERVER;

    static {
        switch (Constant.SERVER) {
            case DEVELOP:
                SERVER = API_DEVELOP_SERVER;
                break;
            case TEST:
                SERVER = API_TEST_SERVER;
                break;
            case CLUSTER:
                SERVER = API_CLUSTER_SERVER;
                break;
        }
    }

}
