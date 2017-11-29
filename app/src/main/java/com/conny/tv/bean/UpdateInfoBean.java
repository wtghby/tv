package com.conny.tv.bean;

import java.io.Serializable;

/**
 * Desc:
 * Created by zhanghui on 2017/11/29.
 */

public class UpdateInfoBean implements Serializable{
    public long id;
    public String fileName;
    public int versionCode;
    public String versionName;
    public boolean update;
    public String details;
}
