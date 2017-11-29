package com.conny.tv.api.service;

import com.conny.tv.bean.ResultBean;
import com.conny.tv.bean.UpdateInfoBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public interface FileService {
    @GET("download")
    Call<ResponseBody> download(@Query("fileName") String fileName);

    @GET("checkUpdate")
    Call<ResultBean<UpdateInfoBean>> checkUpdate(@Query("versionCode") int versionCode, @Query("versionName") String versionName);
}
