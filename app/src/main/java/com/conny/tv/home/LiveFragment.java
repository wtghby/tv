package com.conny.tv.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;

import com.conny.library.pulltorefresh.pulltorefresh.PullToRefreshGridView;
import com.conny.tv.R;
import com.conny.tv.material.base.BaseFragment;

import butterknife.BindView;

/**
 * Desc:
 * Created by zhanghui on 2017/11/23.
 */

public class LiveFragment extends BaseFragment {

    @BindView(R.id.grid)
    PullToRefreshGridView mGrid;

    private ListAdapter adapter;

    @Override
    public View getContainerView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_live, null);
        return view;
    }

    @Override
    public void initData() {
        adapter = new LiveAdapter(mContext, mGrid);
        mGrid.setAdapter(adapter);
    }
}
