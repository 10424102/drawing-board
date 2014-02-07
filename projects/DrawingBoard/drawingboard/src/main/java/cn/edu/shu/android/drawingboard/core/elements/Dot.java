package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.CanvasElement;
import cn.edu.shu.android.drawingboard.core.PaintCanvas;

public class Dot extends Element {

    //Constructor
    public Dot() {
    }

    public Dot(Dot x) {
        super(x);
    }

    public void measureBoundary() {
        mPureWidth = 0;
        mPureHeight = 0;
        setWidth(2 * PADDING);
        setHeight(2 * PADDING);
    }


    @Override
    public void paint(Canvas canvas, Paint paint) {
        if (paint == null) {
            setPaint(mDefaultPaint);
        } else {
            setPaint(paint);
        }
        canvas.drawPoint(getWidth() / 2, getHeight() / 2, mDrawPaint);
    }

    @Override
    public View generate(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            private MyApplication app = MyApplication.getInstance();

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                PaintCanvas pc = app.getPaintCanvas();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        Log.i("yy", "ACTION_DOWN");

                        Dot element = new Dot((Dot) app.getCurrentTool().getContent());
                        element.setGenTool(app.getCurrentTool());
                        element.measureBoundary();

                        CanvasElement canvasElement = new CanvasElement(app.getContext());
                        canvasElement.setContent(element);

                        float centerX = event.getX();
                        float centerY = event.getY();

                        pc.addCanvasElement(canvasElement, new Position(centerX, centerY));

                        break;
                }
                return true;
            }
        });
        return null;
    }
}
