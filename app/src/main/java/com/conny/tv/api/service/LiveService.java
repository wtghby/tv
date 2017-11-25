package com.conny.tv.api.service;

import com.conny.tv.bean.ResultBean;
import com.conny.tv.home.LiveBean;
import com.conny.tv.home.TabBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public interface LiveService {

    @GET("home")
    Call<ResultBean<TabBean>> listTab();

    @GET("category/{type}")
    Call<ResultBean<LiveBean>> listLives(@Path("type") int type);
}
