package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * Created by yy on 2/27/14.
 */
public class Artwork extends FrameLayout {
    private PaintCanvas paintCanvas;

    public PaintCanvas getPaintCanvas() {
        return paintCanvas;
    }

    public Artwork(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (paintCanvas == null) paintCanvas = (PaintCanvas) getParent();
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
    }
}
