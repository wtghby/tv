package com.conny.tv.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.material.base.BaseHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Desc:
 * Created by zhanghui on 2017/11/23.
 */

public class TabAdapter extends BaseAdapter {
    private List<TabBean> mBeans;
    private Context mContext;

    private List<TextView> mItems;
    private int mSelection;

    public TabAdapter(Context context, List<TabBean> beans) {
        mContext = context;
        mBeans = beans;
        mItems = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mBeans == null ? 0 : mBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        TabBean bean = mBeans.get(position);
        holder.item.setText(bean.name);

        if (mSelection == position) {
            holder.item.setSelected(true);
        } else {
            holder.item.setSelected(false);
        }

        return convertView;
    }

    class Holder extends BaseHolder {
        @BindView(R.id.item)
        TextView item;

        public Holder(View view) {
            super(view);
        }
    }

    public void selectItem(int position) {
        mSelection = position;
        notifyDataSetChanged();
    }
}
