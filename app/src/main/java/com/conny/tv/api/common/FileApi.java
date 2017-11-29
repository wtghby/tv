package com.conny.tv.api.common;

import com.conny.tv.api.RetrofitManager;
import com.conny.tv.api.callback.ApiCallback;
import com.conny.tv.api.loading.ProgressHandler;
import com.conny.tv.api.service.FileService;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.bean.UpdateInfoBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class FileApi {
    public static void download(String fileName, Callback<ResponseBody> callback, ProgressHandler handler) {
        FileService fileService = RetrofitManager.getLoadingRetrofit(handler.getListener()).create(FileService.class);
        Call<ResponseBody> call = fileService.download(fileName);
        call.enqueue(callback);
    }

    public static void checkUpdate(int versionCode, String versionName, ApiCallback<UpdateInfoBean> callback) {
        FileService fileService = RetrofitManager.getRetrofit().create(FileService.class);
        Call<ResultBean<UpdateInfoBean>> call = fileService.checkUpdate(versionCode, versionName);

        call.enqueue(callback.getCallback());
    }
}
