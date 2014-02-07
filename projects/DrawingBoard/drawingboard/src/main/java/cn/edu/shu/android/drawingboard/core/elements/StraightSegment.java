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
 * Created by yy on 2/5/14.
 */
public class StraightSegment extends Element {

    private Position mStart;

    public void setStartPosition(Position p) {
        mStart = new Position(p);
    }

    private Position mEnd;

    public void setEndPosition(Position p) {
        mEnd = new Position(p);
    }

    //Constructor
    public StraightSegment() {

    }

    public StraightSegment(StraightSegment x) {
        super(x);
    }

    public void measureBoundary() {
        mPureWidth = Math.abs(mStart.getX() - mEnd.getX());
        mPureHeight = Math.abs(mStart.getY() - mEnd.getY());
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
        float startX = mStart.getX() + getWidth() / 2;
        float startY = -mStart.getY() + getHeight() / 2;
        float endX = mEnd.getX() + getWidth() / 2;
        float endY = -mEnd.getY() + getHeight() / 2;
        canvas.drawLine(startX, startY, endX, endY, mDrawPaint);
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

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("yy", "DOWN");
                        startX = event.getX();
                        startY = event.getY();
                        endX = startX;
                        endY = startY;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("yy", "MOVE");
                        pc.getCanvas().drawLine(startX, startY, endX, endY, erasePaint);
                        endX = event.getX();
                        endY = event.getY();
                        pc.getCanvas().drawLine(startX, startY, endX, endY, drawPaint);
                        pc.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("yy", "UP");
                        pc.getCanvas().drawLine(startX, startY, endX, endY, erasePaint);
                        endX = event.getX();
                        endY = event.getY();

                        StraightSegment element = new StraightSegment((StraightSegment) app.getCurrentTool().getContent());
                        element.setGenTool(app.getCurrentTool());
                        float centerX = (startX + endX) / 2;
                        float centerY = (startY + endY) / 2;
                        startX = startX - centerX;
                        startY = -(startY - centerY);
                        endX = endX - centerX;
                        endY = -(endY - centerY);
                        element.setStartPosition(new Position(startX, startY));
                        element.setEndPosition(new Position(endX, endY));
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
