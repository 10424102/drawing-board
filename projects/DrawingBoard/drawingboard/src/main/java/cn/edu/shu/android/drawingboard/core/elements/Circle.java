package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.CanvasElement;
import cn.edu.shu.android.drawingboard.core.PaintCanvas;

/**
 * Created by yy on 2/6/14.
 */
public class Circle extends Element {
    private float mRadius;

    public void setRadius(float radius) {
        mRadius = radius;
    }

    //Constructor
    public Circle() {

    }

    public Circle(Circle x) {
        super(x);
    }

    public void measureBoundary() {
        mPureWidth = mRadius * 2;
        mPureHeight = mRadius * 2;
        setWidth(mPureWidth + 2 * PADDING);
        setHeight(mPureHeight + 2 * PADDING);
    }


    @Override
    public void paint(Canvas canvas, Paint paint) {
        if (paint == null) {
            setPaint(mDefaultPaint);
        } else {
            setPaint(paint);
        }
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mDrawPaint);
    }

    @Override
    public View generate(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            private float startX;
            private float startY;
            private float endX;
            private float endY;
            private MyApplication app = MyApplication.getInstance();
            private Paint drawPaint = app.getCurrentTool().getDrawPaint();
            private Paint erasePaint = app.getCurrentTool().getErasePaint();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PaintCanvas pc = app.getPaintCanvas();
                float x = event.getX();
                float y = event.getY();
                float dx, dy, r;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("yy", "DOWN");
                        startX = x;
                        startY = y;
                        endX = startX;
                        endY = startY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("yy", "MOVE");
                        dx = endX - startX;
                        dy = endY - startY;
                        r = (float) Math.sqrt(dx * dx + dy * dy);
                        pc.getCanvas().drawCircle(startX, startY, r, erasePaint);
                        endX = x;
                        endY = y;
                        dx = endX - startX;
                        dy = endY - startY;
                        r = (float) Math.sqrt(dx * dx + dy * dy);
                        pc.getCanvas().drawCircle(startX, startY, r, drawPaint);
                        pc.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("yy", "UP");
                        dx = endX - startX;
                        dy = endY - startY;
                        r = (float) Math.sqrt(dx * dx + dy * dy);
                        pc.getCanvas().drawCircle(startX, startY, r, erasePaint);
                        pc.invalidate();

                        Circle element = new Circle((Circle) app.getCurrentTool().getContent());
                        element.setGenTool(app.getCurrentTool());
                        float centerX = startX;
                        float centerY = startY;
                        element.setRadius(r);
                        element.measureBoundary();

                        CanvasElement canvasElement = new CanvasElement(app.getContext());
                        canvasElement.setContent(element);

                        pc.addCanvasElement(canvasElement, new Position(centerX, centerY));

                        break;
                }
                return true;
            }
        });
        return null;
    }

}
