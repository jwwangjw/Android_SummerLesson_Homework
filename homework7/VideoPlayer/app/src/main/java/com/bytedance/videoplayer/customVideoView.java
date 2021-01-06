package com.bytedance.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class customVideoView extends VideoView {
    private int mVideoWidth;
    private int mVideoHeight;

    public customVideoView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public customVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub

    }

    public customVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
