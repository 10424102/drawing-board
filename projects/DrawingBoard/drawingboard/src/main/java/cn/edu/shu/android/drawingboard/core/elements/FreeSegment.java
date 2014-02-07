package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.CanvasElement;
import cn.edu.shu.android.drawingboard.core.PaintCanvas;

/**
 * Created by yy on 2/6/14.
 */
public class FreeSegment extends Element {

    private Path mPath = new Path();
    RectF boundary = new RectF();

    public void setPath(Path path) {
        mPath = path;
    }

    //Constructor
    public FreeSegment() {
    }

    public FreeSegment(FreeSegment x) {
        super(x);
    }

    public Position measureBoundary() {
        mPath.computeBounds(boundary, true);
        mPureWidth = Math.abs(boundary.width());
        mPureHeight = Math.abs(boundary.height());
        mPath.offset(-boundary.left + PADDING, -boundary.top + PADDING);
        setWidth(mPureWidth + 2 * PADDING);
        setHeight(mPureHeight + 2 * PADDING);
        return new Position(boundary.centerX(), boundary.centerY());
    }


    @Override
    public void paint(Canvas canvas, Paint paint) {
        if (paint == null) {
            setPaint(mDefaultPaint);
        } else {
            setPaint(paint);
        }

        canvas.drawPath(mPath, mDrawPaint);
    }

    @Override
    public View generate(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            private Path path;
            private MyApplication app = MyApplication.getInstance();
            private Paint drawPaint = app.getCurrentTool().getDrawPaint();
            private Paint erasePaint = app.getCurrentTool().getErasePaint();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PaintCanvas pc = app.getPaintCanvas();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("yy", "DOWN");
                        path = new Path();
                        path.moveTo(event.getX(), event.getY());
                        pc.getCanvas().drawPath(path, drawPaint);
                        pc.invalidate();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("yy", "MOVE");
                        path.lineTo(event.getX(), event.getY());
                        pc.getCanvas().drawPath(path, drawPaint);
                        pc.invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        pc.getCanvas().drawPath(path, erasePaint);
                        Log.i("yy", "UP");
                        FreeSegment element = new FreeSegment((FreeSegment) app.getCurrentTool().getContent());
                        element.setGenTool(app.getCurrentTool());
                        element.setPath(path);
                        Position center = element.measureBoundary();

                        CanvasElement canvasElement = new CanvasElement(app.getContext());
                        canvasElement.setContent(element);

                        pc.addCanvasElement(canvasElement, center);

                        break;
                }
                return true;
            }
        });
        return null;
    }
}
