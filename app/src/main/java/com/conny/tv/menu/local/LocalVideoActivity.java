package com.conny.tv.menu.local;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.conny.tv.R;
import com.conny.tv.material.base.BaseActivity;
import com.conny.tv.material.utils.VideoUtil;
import com.conny.tv.video.VideoViewActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Desc:
 * Created by zhanghui on 2017/11/29.
 */

public class LocalVideoActivity extends BaseActivity {

    @BindView(R.id.list)
    ListView mList;

    private List<VideoBean> mBeans;
    private LocalVideoAdapter mAdapter;

    @Override
    protected void initLayout() {
        addView(R.layout.activity_local_video);
    }

    @Override
    protected void initData() {
        setTitleTxt(R.string.local_video);
        showProgress(true);
        new VideoThread().start();
    }

    @OnClick({R.id.left_view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_view:
                finish();
                break;
        }
    }


    class VideoThread extends Thread {
        @Override
        public void run() {
            getLoadMedia();
        }
    }

    public void getLoadMedia() {
        Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        try {
            mBeans = new ArrayList<>();
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 显示名称
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)); // 路径
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)); // 大小
                Bitmap thumb = VideoUtil.getVideoThumbnail(path);
                String du = VideoUtil.getRingDuring(path);

                VideoBean bean = new VideoBean();
                bean.title = title;
                bean.path = path;
                bean.duration = Long.parseLong(du);
                bean.size = size;
                bean.thumb = thumb;

                Logger.i(title + "--" + path + "--" + du + "--" + size + "--" + thumb);

                mBeans.add(bean);
            }

            Message message = Message.obtain();
            message.what = 1;
            mHandler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            closeProgress();
            if (msg.what == 1) {
                initList();
            }
        }
    };

    private void initList() {
        if (mBeans == null)
            return;

        mAdapter = new LocalVideoAdapter(this, mBeans);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LocalVideoActivity.this, VideoViewActivity.class);
                intent.putExtra("videoPath", mAdapter.getItem(position).path);
                intent.putExtra("title", mAdapter.getItem(position).title);
                startActivity(intent);
            }
        });
    }


}
