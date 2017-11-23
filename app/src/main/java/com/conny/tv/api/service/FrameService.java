package com.conny.tv.api.service;

import com.conny.tv.bean.ResultBean;
import com.conny.tv.test.IpBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Desc:
 * Created by zhanghui on 2017/11/9.
 */

public interface FrameService {

    @GET("{type}")
    Call<IpBean> mass(@Path("type") String type, @Query("format") String format);

    @GET
    Call<ResultBean<IpBean>> listIp(@Query("page") int page, @Query("pageSize") int pageSize);
}
