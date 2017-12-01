package com.conny.tv.menu.recommend;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.conny.tv.R;
import com.conny.tv.api.ApiManager;
import com.conny.tv.api.callback.ApiCallback;
import com.conny.tv.api.common.FileApi;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.bean.UpdateInfoBean;
import com.conny.tv.material.base.BaseActivity;
import com.conny.tv.material.utils.ApkUtil;
import com.conny.tv.material.utils.QrCodeUtil;
import com.conny.tv.material.utils.ResourcesUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Desc:
 * Created by zhanghui on 2017/12/1.
 */

public class RecommendActivity extends BaseActivity {

    @BindView(R.id.qr_code)
    ImageView mImage;

    private int width;

    @Override
    protected void initLayout() {
        addView(R.layout.activity_recommend);
    }

    @Override
    protected void initData() {
        setTitleTxt(R.string.recommend);
        width = ResourcesUtil.getScreenWidth() / 2;
//        Bitmap bitmap = QrCodeUtil.createQRImage(ApiManager.SERVER + "download?fileName=", width, width);
//        mImage.setImageBitmap(bitmap);
        checkUpdate();
    }

    private void checkUpdate() {
        showProgress(false);
        int versionCode = ApkUtil.getVersionCode(this);
        String versionName = ApkUtil.getVersionName(this);
        FileApi.checkUpdate(versionCode, versionName, new ApiCallback<UpdateInfoBean>() {
            @Override
            public void onSuccess(Call<ResultBean<UpdateInfoBean>> call, ResultBean<UpdateInfoBean> result) {
                closeProgress();
                if (result != null) {
                    UpdateInfoBean info = result.data;
                    if (info != null) {
                        Bitmap bitmap = QrCodeUtil.createQRImage(ApiManager.SERVER + "download?fileName=" + info.fileName, width, width);
                        mImage.setImageBitmap(bitmap);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResultBean<UpdateInfoBean>> call, String message) {
                closeProgress();
            }
        });
    }

    @OnClick({R.id.left_view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_view:
                finish();
                break;
        }
    }
}
