package com.conny.tv.material.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.conny.tv.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Desc:
 * Created by zhanghui on 2017/11/17.
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    private FrameLayout mContent;
    private Unbinder mBinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, null);
        mContent = (FrameLayout) view.findViewById(R.id.content);
        View c = getContainerView();
        mContent.addView(c);

        mBinder = ButterKnife.bind(this, view);

        initData();

        return view;
    }

    public abstract View getContainerView();

    public abstract void initData();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDestroyView() {
        if (mBinder != null) {
            mBinder.unbind();
        }
        super.onDestroyView();
    }

}
