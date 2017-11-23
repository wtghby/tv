package com.conny.tv.material.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * http://www.sjsjw.com/100/007044MYM000708/
 * <p/>
 * 裁剪圆角图片
 *
 * @author zyl
 * @date 2016年6月17日16:21:22
 */
public class GlideRoundTransform extends BitmapTransformation {
    private static float radius = 0f;

    public GlideRoundTransform(Context context) {
        this(context, 4);
    }

    public GlideRoundTransform(Context context, int dp) {
        super(context);
        GlideRoundTransform.radius = Resources.getSystem().getDisplayMetrics().density * dp;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform, outWidth, outHeight);
    }

    /**
     * 图片圆角处理-按图片宽高
     *
     * @param pool
     * @param source
     * @return
     */
    public static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        return roundCrop(pool, source, null, -1, -1);
    }

    /**
     * 图片圆角处理-按控件宽高
     *
     * @param pool
     * @param source
     * @param v      取宽高值的控件
     * @return
     */
    public static Bitmap roundCrop(BitmapPool pool, Bitmap source, View v) {
        return roundCrop(pool, source, v, -1, -1);
    }

    /**
     * 图片圆角处理-按指定宽高
     *
     * @param pool
     * @param source
     * @return
     */
    public static Bitmap roundCrop(BitmapPool pool, Bitmap source, int w, int h) {
        return roundCrop(pool, source, null, w, h);
    }

    /**
     * 图片圆角处理-封装类
     *
     * @param pool
     * @param source
     * @param v      控件
     * @param w      控件宽
     * @param h      控件高
     * @return
     */
    public static Bitmap roundCrop(BitmapPool pool, Bitmap source, View v, int w, int h) {
        if (source == null)
            return null;

        if (v != null) {
            w = v.getMeasuredWidth();
            h = v.getMeasuredHeight();
        }

        int sW = source.getWidth();
        int sH = source.getHeight();

        if (w > 0 && h > 0) {
            float scaleW = w * 1.0F / sW;
            float scaleH = h * 1.0F / sH;
            float scaleVal = Math.max(scaleW, scaleH);

            Matrix m = new Matrix();
            m.postScale(scaleVal, scaleVal);
            source = Bitmap.createBitmap(source, 0, 0, sW, sH, m, true);

            sW = source.getWidth();
            sH = source.getHeight();

            int offsetX = 0;
            int offsetY = 0;

            if (sW > sH) {
                offsetX = (sW - sH) / 2;
            } else {
                offsetY = (sH - sW) / 2;
            }

            source = Bitmap.createBitmap(source, offsetX, offsetY, w, h);
        } else {
            w = source.getWidth();
            h = source.getHeight();
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap target = pool.get(w, h, Bitmap.Config.ARGB_8888);
        if (target == null) {
            target = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, w, h);
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);

        return target;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }

}
