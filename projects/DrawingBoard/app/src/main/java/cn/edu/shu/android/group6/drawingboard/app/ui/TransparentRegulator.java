package cn.edu.shu.android.group6.drawingboard.app.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.App;

/**
 * Created by yy on 2/23/14.
 */
public class TransparentRegulator extends View {
    private final float GRID_SIZE = 5;
    private final int GRID_COLOR_1 = Color.GRAY;
    private final int GRID_COLOR_2 = Color.WHITE;
    private final float POINTER_SIZE = 17;
    private final float POINTER_ANGLE = 70;
    private final float POINTER_MARGIN = 3;
    private float cos = (float) Math.cos(POINTER_ANGLE / 360 * Math.PI);
    private float sin = (float) Math.sin(POINTER_ANGLE / 360 * Math.PI);
    private float dx = POINTER_SIZE * cos * cos;
    private float dy = POINTER_SIZE * cos * sin;
    private float r = POINTER_SIZE * sin;
    private float sx = 3;
    private float sy = r + 10;
    private int width = 30;
    private int length = 250;
    private int color1;
    private int color2;
    private float percentage = 1;
    boolean colorBarChanged = true;
    private float ex = sx + width;
    private float ey = sy + length;
    Paint p1 = new Paint();
    Paint p2 = new Paint();
    Paint p3 = new Paint();
    Paint p4 = new Paint();
    Paint p5 = new Paint();


    public void setColor(int c) {
        color1 = Color.argb(0, Color.red(c), Color.green(c), Color.blue(c));
        color2 = Color.argb(255, Color.red(c), Color.green(c), Color.blue(c));
        p3.setShader(new LinearGradient(sx, sy, ex, ey, color1, color2, Shader.TileMode.MIRROR));
        p4.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
        p5.setColor(Color.argb((int) ((1 - percentage) * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
        invalidate();
    }

    public void setLength(int len) {
        length = len;
        colorBarChanged = true;
        ey = sy + length;
        invalidate();
    }

    public int getColor() {
        return Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2));
    }

    private void init() {
        p1.setColor(GRID_COLOR_1);
        p2.setColor(GRID_COLOR_2);
        p4.setAntiAlias(true);
        p5.setStyle(Paint.Style.STROKE);
        p5.setAntiAlias(true);

        setOnTouchListener(new OnTouchListener() {
            float py;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        py = event.getY();
                        if (py >= sy && py <= ey) {
                            percentage = (py - sy) / length;
                            p4.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            p5.setColor(Color.argb((int) ((1 - percentage) * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            invalidate();
                        } else if (py < sy) {
                            percentage = 0;
                            p4.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            p5.setColor(Color.argb((int) ((1 - percentage) * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            invalidate();
                        } else if (py > ey) {
                            percentage = 1;
                            p4.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            p5.setColor(Color.argb((int) ((1 - percentage) * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        py = event.getY();
                        if (py >= sy && py <= ey) {
                            percentage = (py - sy) / length;
                            p4.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            p5.setColor(Color.argb((int) ((1 - percentage) * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            invalidate();
                        } else if (py < sy) {
                            percentage = 0;
                            p4.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            p5.setColor(Color.argb((int) ((1 - percentage) * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            invalidate();
                        } else if (py > ey) {
                            percentage = 1;
                            p4.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            p5.setColor(Color.argb((int) ((1 - percentage) * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
                            invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        App.getInstance().getPaint().setColor(Color.argb((int) (percentage * 255),
                                Color.red(color2), Color.green(color2), Color.blue(color2)));
                        break;
                }
                return true;
            }
        });
    }


    public TransparentRegulator(Context context) {
        super(context);
        init();
    }

    public TransparentRegulator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TransparentRegulator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = sx;
        float y = sy;
        int k = 1;
        int line = 0;

        while (y <= ey) {
            if (k == 1) {
                canvas.drawRect(x, y, Math.min(x + GRID_SIZE, ex), Math.min(y + GRID_SIZE, ey), p1);
            } else {
                canvas.drawRect(x, y, Math.min(x + GRID_SIZE, ex), Math.min(y + GRID_SIZE, ey), p2);
            }
            x += GRID_SIZE;
            k *= (-1);
            if (x >= ex) {
                x = sx;
                y += GRID_SIZE;
                line++;
                if (line % 2 == 0) {
                    k = 1;
                } else {
                    k = -1;
                }
            }
        }
        canvas.drawRect(sx, sy, ex, ey, p3);
        drawPointer(canvas, ex + POINTER_MARGIN, sy + length * percentage);
    }

    private void drawPointer(Canvas canvas, float x, float y) {
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x + dx, y - dy);
        path.addArc(new RectF(x + POINTER_SIZE - r, y - r, x + POINTER_SIZE + r, y + r), 270 - POINTER_ANGLE / 2, 180 + POINTER_ANGLE);
        path.lineTo(x, y);
        canvas.drawPath(path, p4);
        canvas.drawPath(path, p5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (ex + POINTER_MARGIN + POINTER_SIZE + r + 3), (int) (ey + r + 10));
    }
}
