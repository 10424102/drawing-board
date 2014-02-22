package cn.edu.shu.android.drawingboard.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by otakuplus on 14-2-22.
 */
public class ColorPanel extends ImageView {
    private Paint paint = new Paint();

    public ColorPanel(Context context) {
        super(context);
        paint.setARGB(255,255,255,255);
    }

    public ColorPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setARGB(255,255,255,255);
    }

    public ColorPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint.setARGB(255,255,255,255);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
    }

    public void setARGB(int a, int r, int g, int b) {
        paint.setARGB(a, r, g, b);
    }

    public void setColor(int color){
        paint.setColor(color);
    }
}
