package com.conny.tv.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.conny.library.pulltorefresh.pulltorefresh.AbsGridViewAdapter;
import com.conny.library.pulltorefresh.pulltorefresh.PullToRefreshGridView;
import com.conny.tv.R;
import com.conny.tv.api.RetrofitManager;
import com.conny.tv.api.service.LiveService;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.material.base.BaseHolder;
import com.conny.tv.video.VideoViewActivity;

import butterknife.BindView;
import retrofit2.Call;

/**
 * Desc:
 * Created by zhanghui on 2017/11/23.
 */

public class LiveAdapter extends AbsGridViewAdapter<LiveBean, LiveAdapter.Holder> {
    private int mType;

    public LiveAdapter(Context context, PullToRefreshGridView listView, int type) {
        super(context, listView);
        mType = type;
    }


    @Override
    protected void setViewContent(Holder holder, LiveBean bean, int position) {
        holder.name.setText(bean.name);
    }

    @Override
    protected View createContentItem(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_live_item, null);
        return view;
    }

    @Override
    protected Holder initHolder(View view) {
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    protected Call<ResultBean<LiveBean>> initCall() {
        LiveService liveService = RetrofitManager.getRetrofit().create(LiveService.class);

        return liveService.listLives(mType);
    }

    @Override
    protected void onDataItemClick(int position, LiveBean bean) {
        Intent intent = new Intent(mContext, VideoViewActivity.class);
        intent.putExtra("videoPath", bean.path);
        intent.putExtra("title", bean.name);
        mContext.startActivity(intent);
    }

    @Override
    protected boolean enable() {
        return false;
    }

    class Holder extends BaseHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.desc)
        TextView desc;

        public Holder(View view) {
            super(view);
        }
    }
}
