package com.conny.tv.menu.about;

import android.view.View;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.material.base.BaseActivity;
import com.conny.tv.material.utils.ApkUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Desc:
 * Created by zhanghui on 2017/12/1.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.version_name)
    TextView mVersionName;

    @Override
    protected void initLayout() {
        addView(R.layout.activity_setting);
    }

    @Override
    protected void initData() {
        setTitleTxt(R.string.setting);
        mVersionName.setText(getString(R.string.version_name, ApkUtil.getVersionName(this)));
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
