package com.conny.tv.menu.local;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.material.base.BaseHolder;
import com.conny.tv.material.utils.FileUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Desc:
 * Created by zhanghui on 2017/11/29.
 */

public class LocalVideoAdapter extends BaseAdapter {

    private Context mContext;
    private List<VideoBean> mBeans;

    public LocalVideoAdapter(Context context, List<VideoBean> beans) {
        mContext = context;
        mBeans = beans;
    }

    @Override
    public int getCount() {
        return mBeans == null ? 0 : mBeans.size();
    }

    @Override
    public VideoBean getItem(int position) {
        return mBeans == null ? null : mBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_local_video_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        VideoBean bean = mBeans.get(position);

        holder.icon.setImageBitmap(bean.thumb);
        holder.title.setText(bean.title);
        holder.size.setText(FileUtil.formatFileSize(bean.size));
        holder.duration.setText(FileUtil.formatDuration(bean.duration));

        return convertView;
    }

    class Holder extends BaseHolder {
        @BindView(R.id.icon)
        public ImageView icon;
        @BindView(R.id.title)
        public TextView title;
        @BindView(R.id.duration)
        public TextView duration;
        @BindView(R.id.size)
        public TextView size;

        public Holder(View view) {
            super(view);
        }
    }
}
