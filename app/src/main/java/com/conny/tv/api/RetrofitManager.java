package com.conny.tv.api;

import com.conny.tv.api.loading.ProgressListener;
import com.conny.tv.api.loading.ProgressResponseBody;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Desc:
 * Created by zhanghui on 2017/11/9.
 */

public class RetrofitManager {

    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ApiManager.SERVER)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(genericClient())
                .build();
    }

    private static OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "add cookies here")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

    public static Retrofit getLoadingRetrofit(ProgressListener listener) {
        return new Retrofit.Builder()
                .baseUrl(ApiManager.SERVER)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(genericLoadingClient(listener))
                .build();
    }

    private static OkHttpClient genericLoadingClient(final ProgressListener listener) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "add cookies here")
                                .build();
                        Response origin = chain.proceed(request);

                        return origin.newBuilder().body(new ProgressResponseBody(origin.body(), listener)).build();
                    }

                })
                .build();

        return httpClient;
    }

}
