package com.conny.tv.home;

import com.conny.tv.api.RetrofitManager;
import com.conny.tv.api.callback.ApiCallback;
import com.conny.tv.api.service.LiveService;
import com.conny.tv.bean.ResultBean;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class LiveApi {

    public static void listTab(ApiCallback<TabBean> callback) {
        LiveService liveService = RetrofitManager.getRetrofit().create(LiveService.class);
        Call<ResultBean<TabBean>> call = liveService.listTab();

        call.enqueue(callback.getCallback());
    }
}
