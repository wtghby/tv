package com.conny.tv.test;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.material.base.BaseFragment;

import butterknife.BindView;

/**
 * Desc:
 * Created by zhanghui on 2017/11/17.
 */

public class LazyFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView mTitle;

    private int id;

    @Override
    public View getContainerView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_lazy_fragment, null);
        return view;
    }

    @Override
    public void initData() {
        mTitle.setText("Lazy---" + id);
    }

    public void setId(int id) {
        this.id = id;
    }
}
