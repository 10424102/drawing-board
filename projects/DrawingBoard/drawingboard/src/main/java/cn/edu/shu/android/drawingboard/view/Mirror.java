package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by yy on 2/23/14.
 */
public class Mirror extends FrameLayout {
    private PaintCanvas pc;
    private ImageView shade;

    public void setPaintCanvas(PaintCanvas pc) {
        this.pc = pc;
    }

    public PaintCanvas getPaintCanvas() {
        return pc;
    }

    private void init(Context context) {
        shade = new ImageView(context);
        shade.setBackgroundColor(Color.TRANSPARENT);
        addView(shade);
    }

    public Mirror(Context context) {
        super(context);
        init(context);
    }

    public Mirror(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Mirror(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        //setBackgroundColor(Color.YELLOW);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


}
