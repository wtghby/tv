package com.conny.tv.test;

import com.conny.tv.api.RetrofitManager;
import com.conny.tv.api.service.FrameService;
import com.conny.tv.bean.ResultBean;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Desc:
 * Created by zhanghui on 2017/11/9.
 */

public class FrameApi {

    public static void mass(String format, Callback<IpBean> callback) {
        FrameService service = RetrofitManager.getRetrofit().create(FrameService.class);
        Call<IpBean> call = service.mass("iplookup", format);

        call.enqueue(callback);
    }

    public static void listIp(int page, int pageSize, Callback<ResultBean<IpBean>> callback) {
        FrameService service = RetrofitManager.getRetrofit().create(FrameService.class);
        Call<ResultBean<IpBean>> call = service.listIp(page, pageSize);

        call.enqueue(callback);
    }
}
