package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.App;

/**
 * Created by yy on 2/27/14.
 */
public class ControlPoint extends View {
    private static final App app = App.getInstance();
    public static final int SIZE = 50;
    Paint paint = new Paint();
    private CanvasElement element;
    private List<ControlArc> inArcs = new LinkedList<>();
    private List<ControlArc> outArcs = new LinkedList<>();

    public void update() {
        for (ControlArc ca : inArcs) {
            ca.invalidate();
        }
        for (ControlArc ca : outArcs) {
            ca.invalidate();
        }
    }

    public float getCpX() {
        return getX() + SIZE / 2.0f;
    }

    public float getCpY() {
        return getY() + SIZE / 2.0f;
    }

    public List<ControlArc> getInArcs() {
        return inArcs;
    }

    public List<ControlArc> getOutArcs() {
        return outArcs;
    }

    public CanvasElement getElement() {
        return element;
    }

    public ControlPoint(Context context, CanvasElement e) {
        super(context);
        element = e;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(SIZE, SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() * 0.4f, paint);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() * 0.4f, paint);
        paint.setStrokeWidth(2);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() * 0.25f, paint);
    }
}
