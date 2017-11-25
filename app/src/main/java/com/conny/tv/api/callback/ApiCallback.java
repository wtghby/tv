package com.conny.tv.api.callback;

import com.conny.tv.bean.ResultBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public abstract class ApiCallback<T> {

    private Callback<ResultBean<T>> callback;

    public Callback<ResultBean<T>> getCallback() {
        return callback;
    }

    public ApiCallback() {
        callback = new Callback<ResultBean<T>>() {
            @Override
            public void onResponse(Call<ResultBean<T>> call, Response<ResultBean<T>> response) {
                ApiCallback.this.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ResultBean<T>> call, Throwable t) {
                ApiCallback.this.onFailure(call, t);
            }
        };
    }

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     */
    public abstract void onResponse(Call<ResultBean<T>> call, Response<ResultBean<T>> response);

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    public abstract void onFailure(Call<ResultBean<T>> call, Throwable t);

}
