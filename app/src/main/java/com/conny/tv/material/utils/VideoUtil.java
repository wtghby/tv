package com.conny.tv.material.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

/**
 * Desc:
 * Created by zhanghui on 2017/12/1.
 */

public class VideoUtil {
    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    public static String getRingDuring(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        return duration;
    }
}
