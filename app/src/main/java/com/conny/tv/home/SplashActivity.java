package com.conny.tv.home;

import android.content.Intent;

import com.conny.tv.R;
import com.conny.tv.api.callback.ApiCallback;
import com.conny.tv.api.common.FileApi;
import com.conny.tv.api.loading.ProgressHandler;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.bean.UpdateInfoBean;
import com.conny.tv.material.base.BaseActivity;
import com.conny.tv.material.dialog.CommonDialog;
import com.conny.tv.material.dialog.FileLoadingDialog;
import com.conny.tv.material.utils.ApkUtil;
import com.conny.tv.material.utils.FileUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Desc:
 * Created by zhanghui on 2017/11/29.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void initLayout() {
        addView(R.layout.activity_splash);
    }

    @Override
    protected void initData() {
        checkUpdate();
    }

    private void checkUpdate() {
        int versionCode = ApkUtil.getVersionCode(this);
        String versionName = ApkUtil.getVersionName(this);
        FileApi.checkUpdate(versionCode, versionName, new ApiCallback<UpdateInfoBean>() {
            @Override
            public void onSuccess(Call<ResultBean<UpdateInfoBean>> call, ResultBean<UpdateInfoBean> result) {
                if (result != null) {
                    UpdateInfoBean info = result.data;
                    if (info != null && info.update) {
                        confirmUpdate(info);
                    } else {
                        forward();
                    }
                } else {
                    forward();
                }
            }

            @Override
            public void onFailure(Call<ResultBean<UpdateInfoBean>> call, String message) {
                forward();
            }
        });
    }

    private void confirmUpdate(final UpdateInfoBean info) {
        String message = getString(R.string.update_message, info.versionName);
        final CommonDialog dialog = new CommonDialog(this, message, getString(R.string.update_title));

        dialog.setPositiveButton(R.string.update, new CommonDialog.DialogOnClickListener() {
            @Override
            public void onClick() {
                dialog.dismiss();
                if (FileUtil.hasApk(getApplicationContext(), info.versionCode)) {
                    FileUtil.installApk(getApplicationContext());
                } else {
                    download(info);
                }

            }
        });

        dialog.setNegativeButton(R.string.cancel, new CommonDialog.DialogOnClickListener() {
            @Override
            public void onClick() {
                dialog.dismiss();
                forward();
            }
        });
    }

    private void download(UpdateInfoBean info) {
        if (info == null)
            return;
        final FileLoadingDialog dialog = new FileLoadingDialog(this);
        dialog.setTitle("下载新版本");
        dialog.setName(getString(R.string.version_msg, info.versionName, info.fileName));
        dialog.show();

        FileApi.download(info.fileName, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        FileUtil.saveFile(body.byteStream());
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        }, new ProgressHandler() {
            @Override
            protected void onProgress(long progress, long total, boolean done) {
                dialog.updatePercent(progress, total);
                if (done && dialog.isShowing()) {
                    dialog.dismiss();
                    FileUtil.installApk(getApplicationContext());
                }
            }
        });
    }

    private void forward() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
