package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by yy on 2/21/14.
 */
public class Artwrok extends FrameLayout {
    private PaintCanvas pc;

    public void setPaintCanvas(PaintCanvas pc) {
        this.pc = pc;
    }

    public PaintCanvas getPaintCanvas() {
        return pc;
    }

    public Artwrok(Context context) {
        super(context);
    }

    public Artwrok(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Artwrok(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        int count = this.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View child = this.getChildAt(i);
//            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        //setBackgroundColor(Color.RED);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    public void clear() {
        removeAllViews();
    }
}
