package com.conny.tv.material.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.material.dialog.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Desc:
 * Created by zhanghui on 2017/11/9.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.title_bar)
    protected View mTitleBar;
    @BindView(R.id.title_view)
    protected TextView mTitle;
    @BindView(R.id.left)
    protected ImageView mLeft;

    private FrameLayout mContent;
    private Unbinder mBinder;
    private ProgressDialog mProgress;
    private boolean mCancelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContent = (FrameLayout) findViewById(R.id.content);
        initLayout();
        mBinder = ButterKnife.bind(this);
        initData();
    }

    protected abstract void initLayout();

    protected abstract void initData();

    protected void addView(int layoutId) {
        View view = LayoutInflater.from(this).inflate(layoutId, null);
        mContent.addView(view);
    }

    protected void setTitleTxt(String title) {
        mTitle.setText(title);
    }

    protected void setTitleTxt(int resId) {
        if (resId > 0) {
            mTitle.setText(resId);
        }
    }

    protected void setLeftImage(int resId) {
        mLeft.setImageResource(resId);
    }

    @Override
    protected void onDestroy() {
        if (mBinder != null) {
            mBinder.unbind();
        }
        super.onDestroy();
    }

    public void showProgress(boolean cancelable) {
        mCancelable = cancelable;
        if (mProgress == null) {
            mProgress = new ProgressDialog(this);
        }
        mProgress.setCancelable(mCancelable);

        mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mCancelable = false;
            }
        });

        mProgress.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mCancelable = false;
            }
        });

        if (mProgress != null && !mProgress.isShowing()) {
            mProgress.show();
        }
    }

    public void closeProgress() {
        mCancelable = false;
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }
}
