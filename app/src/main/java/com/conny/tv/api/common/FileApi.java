package com.conny.tv.api.common;

import com.conny.tv.api.RetrofitManager;
import com.conny.tv.api.loading.ProgressHandler;
import com.conny.tv.api.service.FileService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class FileApi {
    public static void download(Callback<ResponseBody> callback, ProgressHandler handler) {
        FileService fileService = RetrofitManager.getLoadingRetrofit(handler.getListener()).create(FileService.class);
        Call<ResponseBody> call = fileService.download();
        call.enqueue(callback);
    }
}
