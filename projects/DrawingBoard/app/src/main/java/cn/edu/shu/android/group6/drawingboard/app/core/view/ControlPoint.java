package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.App;

/**
 * Created by yy on 2/27/14.
 */
public class ControlPoint extends View {
    private static final App app = App.getInstance();
    public static final int SIZE = 50;
    Paint paint = new Paint();

    public ControlPoint(Context context) {
        super(context);
        setOnTouchListener(new OnTouchListener() {
            float sx;
            float sy;
            float px;
            float py;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Paint paint1 = new Paint();
                paint1.setStrokeWidth(3);
                paint1.setAntiAlias(true);
                Paint paint2 = new Paint(paint1);
                paint2.setStrokeWidth(5);
                paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                float pcx = event.getX() + v.getX();
                float pcy = event.getY() + v.getY();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        sx = v.getX() + SIZE / 2;
                        sy = v.getY() + SIZE / 2;
                        px = sx;
                        py = sy;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        app.getPaintCanvas().getDraft().getCanvas().drawLine(sx, sy, px, py, paint2);
                        app.getPaintCanvas().getDraft().getCanvas().drawLine(sx, sy, pcx, pcy, paint1);
                        px = pcx;
                        py = pcy;
                        app.getPaintCanvas().getDraft().invalidate();
                        break;
                    case MotionEvent.ACTION_UP:
                        app.getPaintCanvas().getDraft().getCanvas().drawLine(sx, sy, px, py, paint2);
                        ControlPoint controlPoint = new ControlPoint(getContext());
                        controlPoint.setX(pcx - SIZE / 2);
                        controlPoint.setY(pcy - SIZE / 2);
                        app.getPaintCanvas().getMirror().addView(controlPoint);
//                        app.getPaintCanvas().getDraft().getCanvas().drawLine(sx, sy, pcx, pcy, paint1);
//                        app.getPaintCanvas().getDraft().invalidate();
//                        app.getPaintCanvas().getDraft().setOnTouchListener(new OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//                                return false;
//                            }
//                        });
                        break;
                }
                return true;
            }
        });
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
