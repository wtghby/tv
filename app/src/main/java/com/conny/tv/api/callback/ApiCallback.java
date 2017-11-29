package com.conny.tv.api.callback;

import com.conny.tv.R;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.material.utils.ResourcesUtil;

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
                if (response != null) {
                    ResultBean<T> result = response.body();
                    if (result != null) {
                        if (result.errorCode == 0) {
                            ApiCallback.this.onSuccess(call, result);
                        } else {
                            ApiCallback.this.onFailure(call, result.message);
                        }

                    } else {
                        ApiCallback.this.onFailure(call, response.message());
                    }

                } else {
                    ApiCallback.this.onFailure(call, ResourcesUtil.getString(R.string.get_data_error));
                }

            }

            @Override
            public void onFailure(Call<ResultBean<T>> call, Throwable t) {
                ApiCallback.this.onFailure(call, t.getMessage());
            }
        };
    }

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     */
    public abstract void onSuccess(Call<ResultBean<T>> call, ResultBean<T> result);

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    public abstract void onFailure(Call<ResultBean<T>> call, String message);

}
