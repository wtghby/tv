package com.conny.tv.material.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 能够兼容ViewPager的ScrollView
 * 
 * @Description: 解决了ViewPager在ScrollView中的滑动反弹问题
 */
public class CustomScrollView extends ScrollView {
	private onScrollChangedListener mListener;
	
	public void setOnScrollChangedListener(onScrollChangedListener l){
		mListener = l;
	}
	
	private onScrollEdgeListener mEdgeListener;
	public static final int STATE_EDGE_TOP = 1;		// 顶部
	public static final int STATE_EDGE_BUTTOM = 2;	// 底部
	public static final int STATE_EDGE_MIDDLE = 3;	// 中间
	
	public interface onScrollEdgeListener {
		public void onScrollEdge(int state);
	}
	
	public void setOnScrollEdgeListener(onScrollEdgeListener l) {
		mEdgeListener = l;
	}
	
	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;

	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();

			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;

			if (xDistance > yDistance) {
				return false;
			}
		}

		return super.onInterceptTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (mListener != null) {  
	    	mListener.onScrollChanged(x, y, oldx, oldy);  
	    }
	}

	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
			boolean clampedY) {
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
		if(mEdgeListener != null){
			if(scrollX == 0 && scrollY == 0){
				mEdgeListener.onScrollEdge(STATE_EDGE_TOP);
			} else if(scrollY != 0 && clampedY){
				mEdgeListener.onScrollEdge(STATE_EDGE_BUTTOM);
			} else {
				mEdgeListener.onScrollEdge(STATE_EDGE_MIDDLE);
			}
		}
	}
}
