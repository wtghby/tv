package com.conny.tv.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.conny.library.pulltorefresh.pulltorefresh.AbsGridViewAdapter;
import com.conny.library.pulltorefresh.pulltorefresh.PullToRefreshGridView;
import com.conny.tv.R;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.material.base.BaseHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

/**
 * Desc:
 * Created by zhanghui on 2017/11/23.
 */

public class LiveAdapter extends AbsGridViewAdapter<LiveBean, LiveAdapter.Holder> {

    public LiveAdapter(Context context, PullToRefreshGridView listView) {
        super(context, listView);
    }


    @Override
    protected void setViewContent(Holder holder, LiveBean bean, int position) {

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

        LiveBean l1 = new LiveBean();
        LiveBean l2 = new LiveBean();
        LiveBean l3 = new LiveBean();
        LiveBean l4 = new LiveBean();
        LiveBean l5 = new LiveBean();
        LiveBean l6 = new LiveBean();
        LiveBean l7 = new LiveBean();
        LiveBean l8 = new LiveBean();

        List<LiveBean> list = new ArrayList<>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        list.add(l4);
        list.add(l5);
        list.add(l6);
        list.add(l7);
        list.add(l8);
        addListData(list);
        return null;
    }

    class Holder extends BaseHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.desc)
        TextView desc;

        public Holder(View view) {
            super(view);
        }
    }
}
