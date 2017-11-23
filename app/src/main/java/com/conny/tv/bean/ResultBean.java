package com.conny.tv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:
 * Created by zhanghui on 2017/11/16.
 */

public class ResultBean<T> implements Serializable {
    public int errorCode;
    public String message;
    public int total;
    public int totalPage;
    public T data;
    public List<T> rows;
}
