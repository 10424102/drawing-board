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
    public ColorPanel(Context context) {
        super(context);
    }

    public ColorPanel(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ColorPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Paint paint = new Paint();
        paint.setARGB(255, 100, 100, 40);
        canvas.drawCircle(width/2, height/2, width/2, paint);
    }
}
