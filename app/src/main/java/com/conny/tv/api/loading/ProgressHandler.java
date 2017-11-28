package com.conny.tv.api.loading;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public abstract class ProgressHandler {
    private ProgressListener listener;

    public ProgressHandler() {
        initListener();
    }

    private void initListener() {
        listener = new ProgressListener() {
            @Override
            public void onProgress(long progress, long total, boolean done) {
                ProgressBean bean = new ProgressBean();
                bean.read = progress;
                bean.total = total;
                bean.done = done;
                sendMessage(bean);
            }
        };
    }

    public ProgressListener getListener() {
        return listener;
    }

    private static final int DOWNLOAD_PROGRESS = 1;
    private Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_PROGRESS:
                    ProgressBean bean = (ProgressBean) msg.obj;
                    onProgress(bean.read, bean.total, bean.done);
                    break;
            }
        }
    };

    public void sendMessage(ProgressBean bean) {
        Message message = Message.obtain();
        message.what = DOWNLOAD_PROGRESS;
        message.obj = bean;
        handler.sendMessage(message);
    }

    protected abstract void onProgress(long progress, long total, boolean done);

}
