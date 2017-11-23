package com.conny.tv.material.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.conny.tv.R;

public class ToastUtil {
	private static Toast mToast = null;

	public void showToast(Context context, String text, int duration) {
		try {
			if (mToast == null) {
				mToast = Toast.makeText(context.getApplicationContext(), text,
						duration);
			} else {
				mToast.setText(text);
				mToast.setDuration(duration);
			}
			mToast.show();
		} catch (Exception e) {

		}
	}

	public void showToast(Context context, int resId, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context.getApplicationContext(), resId,
					duration);
		} else {
			mToast.setText(resId);
			mToast.setDuration(duration);
		}
		mToast.show();
	}

	public static void cancel() {
		if (mToast != null) {
			mToast.cancel();
		}
	}

	public static void showView(Context ctx, View view, int duration,
                                int gravity) {
		if (mToast == null) {
			mToast = new Toast(ctx.getApplicationContext());
		}
		mToast.setView(view);
		mToast.setGravity(gravity, 0, 0);
		mToast.setDuration(duration);
		mToast.show();
	}

	public static void showCustomViewToast(Context ctx, int resImgId,
                                           String text, int duration) {
		View v = LayoutInflater.from(ctx.getApplicationContext()).inflate(
				R.layout.base_custom_toast, null, false);
		if (resImgId > 0) {
			ImageView iv = (ImageView) v.findViewById(R.id.toast_img);
			iv.setImageDrawable(ResourcesUtil.getDrawable(resImgId));
		}
		TextView tv = (TextView) v.findViewById(R.id.toast_content);
		tv.setText(text);
		showView(ctx, v, 3000, Gravity.CENTER);
	}

	public static void showCustomViewToast(Context ctx, int resImgId,
                                           int textId, int duration) {
		showCustomViewToast(ctx, resImgId, ctx.getString(textId), duration);
	}

	public static void showCustomViewToast(Context ctx, int textId, int duration) {
		showCustomViewToast(ctx, 0, ctx.getString(textId), duration);
	}

	public static void showCustomViewToast(Context ctx, String textId,
                                           int duration) {
		showCustomViewToast(ctx, 0, textId, duration);
	}

	public static void showCustomViewToast(Context ctx, int textId) {
		showCustomViewToast(ctx, 0, ctx.getString(textId), 3000);
	}

	public static void showCustomViewToast(Context ctx, String textId) {
		showCustomViewToast(ctx, 0, textId, 3000);
	}
}
