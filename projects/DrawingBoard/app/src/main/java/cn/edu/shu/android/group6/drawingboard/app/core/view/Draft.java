package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;

/**
 * Created by yy on 2/27/14.
 */
public class Draft extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();
    private Paint erasePaint = new Paint();
    private PaintCanvas paintCanvas;

    public PaintCanvas getPaintCanvas() {
        return paintCanvas;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void clear() {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), erasePaint);
    }

    public Draft(Context context) {
        super(context);
        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (paintCanvas == null) paintCanvas = (PaintCanvas) getParent();
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        bitmap = Bitmap.createBitmap(parentWidth, parentHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}
