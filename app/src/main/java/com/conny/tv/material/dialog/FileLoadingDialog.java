package com.conny.tv.material.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.material.utils.ResourcesUtil;

/**
 * Desc:
 * Created by zhanghui on 2017/11/28.
 */

public class FileLoadingDialog extends Dialog {

    private TextView mTitle;
    private TextView mName;
    private TextView mPercent;
    private ProgressBar mProgress;

    private String title;
    private String name;

    public FileLoadingDialog(@NonNull Context context) {
        super(context, R.style.Theme_AppCompat_Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_file_loading);
        int width = ResourcesUtil.getScreenWidth();
        width -= 2 * ResourcesUtil.getDimensionPixelSize(R.dimen.px50);
        getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        setCancelable(false);

        mTitle = (TextView) findViewById(R.id.title);
        mName = (TextView) findViewById(R.id.name);
        mPercent = (TextView) findViewById(R.id.percent);
        mProgress = (ProgressBar) findViewById(R.id.progress);

        mTitle.setText(title);
        mName.setText(name);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updatePercent(long progress, long total) {
        int percent = (int) (100 * ((progress * 1.0) / total));
        String ps = percent + "%";
        mPercent.setText(ps);
        mProgress.setProgress(percent);
    }
}
