package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.graphics.Color;
import android.widget.FrameLayout;

/**
 * Created by yy on 2/27/14.
 */
public class Mirror extends FrameLayout {
    private PaintCanvas paintCanvas;
    private ControlPoint activeControlPoint;
    private Shade shade = new Shade(getContext());

    public ControlPoint getActiveControlPoint() {
        return activeControlPoint;
    }

    public void setActiveControlPoint(ControlPoint activeControlPoint) {
        this.activeControlPoint = activeControlPoint;
    }

    public PaintCanvas getPaintCanvas() {
        return paintCanvas;
    }

    public Mirror(Context context) {
        super(context);
        addView(shade);
    }

    @Override
    public void removeAllViews() {
        //super.removeAllViews();
        int count = getChildCount();
        for (int i = 1; i < count; i++) {
            removeView(getChildAt(1));
        }
    }

    public void shadeOn() {
        shade.setBackgroundColor(Color.argb(200, 0, 0, 0));
//        shade.setOpen(true);
//        shade.invalidate();
    }

    public void shadeOff() {
        shade.setBackgroundColor(Color.TRANSPARENT);
//        shade.setOpen(false);
//        shade.invalidate();
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
