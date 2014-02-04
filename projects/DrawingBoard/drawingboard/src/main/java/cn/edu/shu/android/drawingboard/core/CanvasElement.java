package cn.edu.shu.android.drawingboard.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yy on 2/4/14.
 */
public class CanvasElement extends View implements View.OnTouchListener {
    private int left = 0;
    private int top = 0;
    private boolean selected = false;
    public static final int SELECTED_PADDING = 20;
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;
    private float centerX = (float) (DEFAULT_WIDTH / 2.0);
    private float centerY = (float) (DEFAULT_HEIGHT / 2.0);

    public void setWidth(int w) {
        width = w;
        centerX = (float) (w / 2.0);
    }

    public void setHeight(int h) {
        height = h;
        centerY = (float) (h / 2.0);
    }

    private Bitmap bmp;
    private Canvas c;
    private Paint pen;

    private void init() {

        bmp = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        c = new Canvas(bmp);
        c.drawColor(Color.TRANSPARENT);
        pen = new Paint(Color.RED);
        pen.setStrokeWidth(3);
        pen.setColor(Color.RED);
        pen.setAntiAlias(true);
        c.drawPoint(centerX, centerY, pen);
        pen.setStyle(Paint.Style.STROKE);
        pen.setColor(Color.BLUE);
        c.drawCircle(centerX, centerY, 30, pen);
        pen.setColor(Color.TRANSPARENT);
        c.drawCircle(centerX, centerY, 30, pen);
        pen.setColor(Color.GRAY);
        pen.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        c.drawRect(0, 0, width, height, pen);
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, 0, 0, pen);
    }

    public CanvasElement(Context context) {
        super(context);
        init();
    }

    public CanvasElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasElement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!selected) {
                    selected = true;
                    pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                    pen.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
                    pen.setStrokeWidth(10);
                    pen.setColor(Color.RED);
                    c.drawRect(0, 0, width, height, pen);
                    invalidate();
                } else {
                    selected = false;
                    pen.setColor(Color.TRANSPARENT);
                    pen.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                    pen.setPathEffect(new DashPathEffect(new float[]{5}, 0));
                    pen.setStrokeWidth(20);
                    c.drawRect(0, 0, width, height, pen);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
