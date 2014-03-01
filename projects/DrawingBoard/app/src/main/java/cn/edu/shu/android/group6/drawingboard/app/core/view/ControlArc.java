package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by yy on 3/2/14.
 */
public class ControlArc extends View {
    private ControlPoint from;
    private ControlPoint to;
    private Paint paint = new Paint();

    public ControlPoint getFrom() {
        return from;
    }

    public void setFrom(ControlPoint from) {
        this.from = from;
    }

    public ControlPoint getTo() {
        return to;
    }

    public void setTo(ControlPoint to) {
        this.to = to;
    }

    public ControlArc(Context context) {
        super(context);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(from.getX() + ControlPoint.SIZE / 2, from.getY() + ControlPoint.SIZE / 2, to.getX() + ControlPoint.SIZE / 2, to.getY() + ControlPoint.SIZE / 2, paint);
    }
}
