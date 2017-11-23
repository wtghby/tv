package com.conny.tv.material.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conny.tv.R;

public class ProgressDialog extends Dialog {
    private TextView mTvTip;
    private String mTxt;

    public ProgressDialog(Context context) {
        super(context, R.style.common_dialog2);
    }

    public void setText(String txt) {
        mTxt = txt;
    }

    public void setText(int res) {
        if (res > 0)
            mTxt = getContext().getString(res);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mTvTip = (TextView) findViewById(R.id.progress_txt);
        if (!TextUtils.isEmpty(mTxt)) {
            mTvTip.setText(mTxt);
        }
        setCanceledOnTouchOutside(false);

    }

}
