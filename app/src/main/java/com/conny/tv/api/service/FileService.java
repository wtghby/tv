package com.conny.tv.api.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public interface FileService {
    @GET("file/download")
    Call<ResponseBody> download();
}
