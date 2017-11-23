package com.conny.tv.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.bean.ResultBean;
import com.conny.tv.material.base.BaseHolder;
import com.conny.library.pulltorefresh.pulltorefresh.AbsSwipeAdapter;
import com.conny.library.pulltorefresh.pulltorefresh.PullToRefreshSwipeListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;

/**
 * Desc:
 * Created by zhanghui on 2017/11/17.
 */

public class SwipeAdapter extends AbsSwipeAdapter<IpBean, SwipeAdapter.Holder> {

    public SwipeAdapter(Context context, PullToRefreshSwipeListView listView) {
        super(context, listView);
    }

    @Override
    protected void setViewContent(Holder holder, IpBean bean, int position) {
        holder.title.setText(bean.country);
        holder.desc.setText(bean.province + "--" + bean.city);
    }

    @Override
    protected View createContentItem(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_swipe_item, null);
        return view;
    }

    @Override
    protected Holder initHolder(View view) {
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    protected Call<ResultBean<IpBean>> initCall() {
        List<IpBean> beans = new ArrayList<>();

        IpBean b1 = new IpBean();
        b1.country = "中国";
        b1.province = "四川省";
        b1.city = "华蓥市";

        IpBean b2 = new IpBean();
        b2.country = "中国";
        b2.province = "贵州省";
        b2.city = "贵阳市";

        IpBean b3 = new IpBean();
        b3.country = "中国";
        b3.province = "湖南省";
        b3.city = "长沙市";

        IpBean b4 = new IpBean();
        b4.country = "中国";
        b4.province = "重庆市";
        b4.city = "渝北区";

        IpBean b5 = new IpBean();
        b5.country = "中国";
        b5.province = "四川省";
        b5.city = "华蓥市";

        beans.add(b1);
        beans.add(b2);
        beans.add(b3);
        beans.add(b4);
        beans.add(b5);

        addListData(beans);
        return null;
    }

    public class Holder extends BaseHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.desc)
        TextView desc;

        public Holder(View view) {
            super(view);
        }
    }
}
