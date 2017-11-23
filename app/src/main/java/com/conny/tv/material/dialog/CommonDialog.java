package com.conny.tv.material.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.conny.tv.R;
import com.conny.tv.material.utils.LogUtils;
import com.conny.tv.material.utils.ResourcesUtil;

/*
 * Copyright (C) 2017 重庆呼我出行网络科技有限公司
 * 版权所有
 *
 * 功能描述：通用dialog
 * 作者：mikeyou
 * 创建时间：2017-10-6
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */

public class CommonDialog extends Dialog implements OnItemClickListener {

    public static final String TAG = "CommonDialog";
    /**
     * 普通文字对话框
     */
    protected final int STYLE_WORD_TYPE = 100;
    /**
     * 列表选项对话框
     */
    protected final int STYLE_LIST_TYPE = 102;

    public static final int CUSTOM_VIEW = 105;

    // 列表框类型的子类型
    public static final int LIST_SUB_STYLE_NORMAL = 200;
    // 靠底部，并有取消的选项
    public static final int LIST_SUB_STYLE_AGLIN_BOTTOM = 201;

    private int currentStyle = STYLE_WORD_TYPE;
    private int subStyle = LIST_SUB_STYLE_NORMAL;

    private LinearLayout mContainer;
    private int mBgResId = -1;
    private FrameLayout mTopContainer;
    private LinearLayout mContainerWithTitle;
    private Spanned message;
    private Spanned title;
    private Context mContext;
    private TextView mTitle;
    private TextView mMessageNoTitle;
    private TextView mMessageWithTitle;
    private ListView mListView;
    private Button btnOK;
    private Button btnCancel;
    private View btnDivider;
    private String[] listTexts;
    private int[] listImages;
    private DialogItemOnClick listItemClickListener;
    private DialogOnClickListener positiveListener;
    private DialogOnClickListener negativeListener;
    private int positiveText;
    private int negativeText;
    private View btnContent;
    private TextView mBottomCancel;
    private int mMsgGravity = -1;
    private View mCustomView;

    private boolean mCancellable = true;

    private boolean mHeightLimit = true;

    public interface DialogOnClickListener {
        public void onClick();
    }

    public interface DialogItemOnClick {
        public void onItemClick(int position, String text);
    }

    public CommonDialog(Context ctx, View customView) {
        super(ctx, R.style.common_dialog);
        if (customView == null) {
            LogUtils.d(TAG, "customView is null");
        }

        mContext = ctx;
        mCustomView = customView;
        currentStyle = CUSTOM_VIEW;
    }

    /**
     * 普通文字对话框
     */
    public CommonDialog(Context context, int msg, int title) {
        super(context, R.style.common_dialog);
        if (msg <= 0) {
            try {
                throw (new Throwable("message is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_WORD_TYPE, title, msg, 0, null);
        }
    }

    /**
     * 普通文字对话框
     */
    public CommonDialog(Context context, int msg, String title) {
        super(context, R.style.common_dialog);
        if (msg <= 0) {
            try {
                throw (new Throwable("message is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_WORD_TYPE, title, msg, 0, null);
        }
    }

    /**
     * 普通文字对话框
     */
    public CommonDialog(Context context, Spanned msg, int title) {
        super(context, R.style.common_dialog);
        if (TextUtils.isEmpty(msg)) {
            try {
                throw (new Throwable("message is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_WORD_TYPE, title, msg, 0, null);
        }
    }

    /**
     * 普通文字对话框
     */
    public CommonDialog(Context context, CharSequence msg, int title) {
        super(context, R.style.common_dialog);
        if (TextUtils.isEmpty(msg)) {
            try {
                throw (new Throwable("message is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_WORD_TYPE, title, new SpannedString(msg), 0,
                    null);
        }
    }

    /**
     * 普通文字对话框
     */
    public CommonDialog(Context context, CharSequence msg, String title) {
        super(context, R.style.common_dialog);
        if (TextUtils.isEmpty(msg)) {
            try {
                throw (new Throwable("message is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_WORD_TYPE, title, new SpannedString(msg), 0,
                    null);
        }
    }

    /**
     * 列表选项对话框
     */
    public CommonDialog(Context context, int title, int txtsArrayId, int[] images) {
        super(context, R.style.common_dialog);
        if (txtsArrayId <= 0) {
            try {
                throw (new Throwable("list item text is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_LIST_TYPE, title, 0, txtsArrayId, images);
        }
    }

    /**
     * 列表选项对话框
     */
    public CommonDialog(Context context, int title, String[] txtsArray,
                        int[] images) {
        super(context, R.style.common_dialog);
        if (txtsArray == null) {
            try {
                throw (new Throwable("list item text is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_LIST_TYPE, title, 0, txtsArray, images);
        }
    }

    /**
     * 列表选项对话框
     */
    public CommonDialog(Context context, int title, int txtsArrayId,
                        int[] images, int subType) {
        super(context, R.style.common_dialog);
        this.subStyle = subType;
        if (txtsArrayId <= 0) {
            try {
                throw (new Throwable("list item text is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_LIST_TYPE, title, 0, txtsArrayId, images);
        }
    }

    /**
     * 列表选项对话框
     */
    public CommonDialog(Context context, int title, String[] txtsArray,
                        int[] images, int subType) {
        super(context, R.style.common_dialog);
        this.subStyle = subType;
        if (txtsArray == null) {
            try {
                throw (new Throwable("list item text is null"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            init(context, STYLE_LIST_TYPE, title, 0, txtsArray, images);
        }
    }

    private void init(Context context, int styleId, int titleId, int msgId,
                      String[] txtsArray, int[] images) {
        mContext = context;
        currentStyle = styleId;
        listImages = images;
        listTexts = txtsArray;
        if (msgId != 0) {
            message = new SpannedString(mContext.getString(msgId));
        }
        if (titleId != 0) {
            this.title = new SpannedString(mContext.getString(titleId));
        }
    }

    private void init(Context context, int style, String title, int msg,
                      int txtsArrayId, int[] images) {
        mContext = context;
        currentStyle = style;
        listImages = images;
        if (!TextUtils.isEmpty(title)) {
            this.title = new SpannedString(title);
        }
        if (msg != 0) {
            message = new SpannedString(mContext.getString(msg));
        }
        if (txtsArrayId != 0) {
            listTexts = mContext.getResources().getStringArray(txtsArrayId);
        }
    }

    private void init(Context context, int style, int title, Spanned msg,
                      int txtsArrayId, int[] images) {
        mContext = context;
        currentStyle = style;
        message = msg;
        if (title > 0) {
            this.title = new SpannedString(mContext.getString(title));
        }
        listImages = images;
        if (txtsArrayId != 0) {
            listTexts = mContext.getResources().getStringArray(txtsArrayId);
        }
    }

    private void init(Context context, int style, String title, Spanned msg,
                      int txtsArrayId, int[] images) {
        mContext = context;
        currentStyle = style;
        message = msg;
        if (!TextUtils.isEmpty(title)) {
            this.title = new SpannedString(title);
        }
        listImages = images;
        if (txtsArrayId != 0) {
            listTexts = mContext.getResources().getStringArray(txtsArrayId);
        }
    }

    private void init(Context context, int styleId, int titleId, int msgId,
                      int txtsArrayId, int[] images) {
        mContext = context;
        currentStyle = styleId;
        listImages = images;
        if (txtsArrayId > 0) {
            listTexts = mContext.getResources().getStringArray(txtsArrayId);
        }
        if (msgId != 0) {
            message = new SpannedString(mContext.getString(msgId));
        }
        if (txtsArrayId != 0) {
            listTexts = mContext.getResources().getStringArray(txtsArrayId);
        }
        if (titleId != 0) {
            this.title = new SpannedString(mContext.getString(titleId));
        }
    }

    public void setHeightLimit(boolean limit) {
        mHeightLimit = limit;
    }

    public void setBgRes(int resId) {
        mBgResId = resId;
        if (mContainer != null) {
            mContainer.setBackgroundResource(resId);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_layout_common_dialog);
        /**
         * 设置对话框属性
         */
        Window dialogWindow = this.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        int width = dialogWindow.getWindowManager().getDefaultDisplay()
                .getWidth();

        if (LIST_SUB_STYLE_AGLIN_BOTTOM == subStyle) {
            dialogWindow.setGravity(Gravity.BOTTOM);
        } else {
            width -= 2 * getContext().getResources().getDimensionPixelSize(
                    R.dimen.px30);
            dialogWindow.setGravity(Gravity.CENTER);
        }

        params.dimAmount = 0.5f;
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogWindow.setAttributes(params);
        setCanceledOnTouchOutside(mCancellable);
        initView();
        setInfo();
        if (mBgResId != -1) {
            if (mContainer != null) {
                mContainer.setBackgroundResource(mBgResId);
            }
        }

        View v = dialogWindow.getDecorView();
        v.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int height = v.getMeasuredHeight();
        if (mHeightLimit && height > ResourcesUtil.getScreenHeight() * 2 / 3) {
            height = ResourcesUtil.getScreenHeight() * 2 / 3;
            if (mTopContainer != null) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, height);
                mTopContainer.setLayoutParams(lp);
            }
        }
        dialogWindow.setLayout(width, LayoutParams.WRAP_CONTENT);

    }

    public void setCancellable(boolean cancel) {
        mCancellable = cancel;
    }

    protected void setInfo() {
        setTitleStatus();
        setBtnStatus();
        switch (currentStyle) {
            case STYLE_WORD_TYPE:
                if (!TextUtils.isEmpty(title)) {
                    mMessageWithTitle.setText(message);
                    mMessageWithTitle.setVisibility(View.VISIBLE);
                    mContainerWithTitle.setVisibility(View.VISIBLE);
                    if (mMsgGravity >= 0) {
                        mMessageWithTitle.setGravity(mMsgGravity);
                    }
                } else {
                    mMessageNoTitle.setText(message);
                    mMessageNoTitle.setVisibility(View.VISIBLE);
                    if (mMsgGravity > 0) {
                        mMessageNoTitle.setGravity(mMsgGravity);
                    }
                }

                break;
//            case STYLE_LIST_TYPE:
//                mListView.setAdapter(new DialogListAdapter(mContext, this,
//                        listTexts, listImages,
//                        subStyle != LIST_SUB_STYLE_AGLIN_BOTTOM));
//                mListView.setOnItemClickListener(this);
//                mListView.setVisibility(View.VISIBLE);
//                if (subStyle == LIST_SUB_STYLE_AGLIN_BOTTOM) {
//                    mContainer.setBackgroundDrawable(new BitmapDrawable());
//                    mContainer.setBackgroundColor(ResourcesUtil
//                            .getColor(R.color.common_grey_bg_color));
//                    mBottomCancel.setVisibility(View.VISIBLE);
//                    mBottomCancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            cancel();
//                        }
//
//                    });
//
//                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//                    lp.setMargins(0, getContext().getResources()
//                            .getDimensionPixelOffset(R.dimen.px30), 0, 0);
//
//                    mListView.setLayoutParams(lp);
//                    setCanceledOnTouchOutside(true);
//                }
//                break;
            case CUSTOM_VIEW: {
                if (mCustomView != null) {
                    mTopContainer.addView(mCustomView);
                }
            }
            default:
                break;
        }
    }

    protected void setTitleStatus() {
        if (mCustomView != null) {
            return;
        }
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
            mContainerWithTitle.setVisibility(View.VISIBLE);
            if (currentStyle != STYLE_WORD_TYPE) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContainerWithTitle
                        .getLayoutParams();
                lp.height = LayoutParams.WRAP_CONTENT;
                mContainerWithTitle.setLayoutParams(lp);
            }
        } else if (currentStyle == STYLE_WORD_TYPE) {
            mMessageNoTitle.setVisibility(View.VISIBLE);
        }
    }

    protected void setBtnStatus() {
        if (positiveText > 0 && positiveListener != null) {
            btnOK.setText(positiveText);
            btnOK.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    positiveListener.onClick();
                }
            });
            btnOK.setVisibility(View.VISIBLE);
        }
        if (negativeText > 0 && negativeListener != null) {
            btnCancel.setText(negativeText);
            btnCancel
                    .setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            negativeListener.onClick();
                        }
                    });
            btnCancel.setVisibility(View.VISIBLE);
        }
        if ((positiveText > 0 && positiveListener != null)
                && (negativeText > 0 && negativeListener != null)) {
            btnContent.setVisibility(View.VISIBLE);
            btnDivider.setVisibility(View.VISIBLE);
        } else if ((positiveText > 0 && positiveListener != null)
                || (negativeText > 0 && negativeListener != null)) {
            btnContent.setVisibility(View.VISIBLE);
//			btnOK.setBackgroundResource(R.drawable.common_white_btn_cornor_selector);
//			btnCancel
//					.setBackgroundResource(R.drawable.common_white_btn_cornor_selector);
        } else {
            btnContent.setVisibility(View.GONE);
        }
    }

    public void setPositiveButton(int textId,
                                  final DialogOnClickListener listener) {
        if (textId <= 0 || listener == null) {
            try {
                throw (new Throwable(
                        "please set button text and OnClickListener"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            positiveText = textId;
            positiveListener = listener;
        }
    }

    public void setNegativeButton(int textId,
                                  final DialogOnClickListener listener) {
        if (textId <= 0 || listener == null) {
            try {
                throw (new Throwable(
                        "please set button text and OnClickListener"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            negativeText = textId;
            negativeListener = listener;
        }
    }

    public void setItemClickListener(DialogItemOnClick listener) {
        listItemClickListener = listener;
    }

    public void initView() {
        mContainer = (LinearLayout) findViewById(R.id.container);
        mTopContainer = (FrameLayout) findViewById(R.id.dialog_top);
        mContainerWithTitle = (LinearLayout) findViewById(R.id.dialog_with_title);
        mMessageWithTitle = (TextView) findViewById(R.id.dialog_msg);
        mMessageNoTitle = (TextView) findViewById(R.id.dialog_msg_no_title);
        mListView = (ListView) findViewById(R.id.dialog_list);
        btnCancel = (Button) findViewById(R.id.dialog_cancel);
        btnOK = (Button) findViewById(R.id.dialog_ok);
        btnDivider = findViewById(R.id.btn_divider);
        mTitle = (TextView) findViewById(R.id.dialog_title);
        btnContent = findViewById(R.id.dialog_btn_content);
        mBottomCancel = (TextView) findViewById(R.id.image_chooser_dialog_cancel);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        cancel();
        listItemClickListener.onItemClick(position, listTexts[position]);
    }

    public void setMsgAlign(int gravity) {
        mMsgGravity = gravity;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!mCancellable && keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
