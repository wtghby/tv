package com.conny.tv.api;

/**
 * Desc:
 * Created by zhanghui on 2017/11/9.
 */

public class ApiManager {

    //正式发版时需要置为true
    public static final boolean RELEASE = false;

    private static final String API_DEVELOP_SERVER = "http://int.dpool.sina.com.cn/";
    private static final String API_TEST_SERVER = "";
    private static final String API_CLUSTER_SERVER = "";

    public static String SERVER = API_DEVELOP_SERVER;

}
