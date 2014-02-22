package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import cn.edu.shu.android.drawingboard.core.elements.Element;

/**
 * Created by yy on 2/21/14.
 */
public class CanvasElement extends View {
    private Element content;

    public void setContent(Element e) {
        content = e;
    }

    public CanvasElement(Context context) {
        super(context);
    }

    public CanvasElement(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasElement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        content.paint(canvas);
    }
}
