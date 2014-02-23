package cn.edu.shu.android.drawingboard.ui;

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

/**
 * Created by yy on 2/23/14.
 */
public class TransparentRegulator extends View {
    private final int GRID_SIZE = 5;
    private final int GRID_COLOR_1 = Color.GRAY;
    private final int GRID_COLOR_2 = Color.WHITE;
    private int width = 25;
    private int length = 250;
    private int color1;
    private int color2;
    private double percentage = 1;

    public void setColor(int c) {
        color1 = Color.argb(0, Color.red(c), Color.green(c), Color.blue(c));
        color2 = Color.argb(255, Color.red(c), Color.green(c), Color.blue(c));
        invalidate();
    }

    public void setLength(int len) {
        length = len;
        invalidate();
    }

    public int getColor() {
        return Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2));
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {
            float px;
            float py;
            boolean mark = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        py = event.getY();
//                        if (py > length * percentage - 24 && py < length * percentage + 24) {
//                            mark = true;
//                        }
                        if (py >= 0 && py <= length) {
                            mark = true;
                            percentage = py / length;
                            invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mark) {
                            py = event.getY();
                            if (py >= 0 && py <= length) {
                                percentage = py / length;
                                invalidate();
                            }

                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        mark = false;
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
        int x = 0;
        int y = 0;
        int k = 1;
        int line = 0;
        Paint p1 = new Paint();
        p1.setColor(GRID_COLOR_1);
        Paint p2 = new Paint();
        p2.setColor(GRID_COLOR_2);
        while (y < length) {
            if (k == 1) {
                canvas.drawRect(x, y, x + GRID_SIZE, y + GRID_SIZE, p1);
            } else {
                canvas.drawRect(x, y, x + GRID_SIZE, y + GRID_SIZE, p2);
            }
            x += GRID_SIZE;
            k *= (-1);
            if (x >= width) {
                x = 0;
                y += GRID_SIZE;
                line++;
                if (line % 2 == 0) {
                    k = 1;
                } else {
                    k = -1;
                }
            }
        }

        Paint p3 = new Paint();
        p3.setShader(new LinearGradient(0, 0, width, length, color1, color2, Shader.TileMode.MIRROR));
        canvas.drawRect(0, 0, width, length, p3);

        drawPointer(canvas, width + 3, length * percentage, 17, 70);

    }

    private void drawPointer(Canvas canvas, double x, double y, double len, double angle) {
        double cos = Math.cos(angle / 360 * Math.PI);
        double sin = Math.sin(angle / 360 * Math.PI);
        double dx = len * cos * cos;
        double dy = len * cos * sin;
        double r = len * sin;
        Path path = new Path();
        path.moveTo((float) x, (float) y);
        path.lineTo((float) (x + dx), (float) (y - dy));
        path.addArc(new RectF((float) (x + len - r), (float) (y - r), (float) (x + len + r), (float) (y + r)), (float) (270 - angle / 2), (float) (180 + angle));
        path.lineTo((float) x, (float) y);
        //path.close();
        Paint p1 = new Paint();
        p1.setAntiAlias(true);
        p1.setColor(Color.argb((int) (percentage * 255), Color.red(color2), Color.green(color2), Color.blue(color2)));
        //p1.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, p1);
        Paint p2 = new Paint();
        p2.setStyle(Paint.Style.STROKE);
        p2.setAntiAlias(true);
        //canvas.drawPath(path, p2);
    }
}
