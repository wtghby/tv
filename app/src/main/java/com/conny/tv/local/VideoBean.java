package com.conny.tv.local;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Desc:
 * Created by zhanghui on 2017/11/29.
 */

public class VideoBean implements Serializable {
    public String title;
    public String path;
    public long duration;
    public long size;
    public Bitmap thumb;
}
