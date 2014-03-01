package cn.edu.shu.android.group6.drawingboard.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.View;

/**
 * Created by yy on 2/26/14.
 */
public class CustomView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();
    private Paint erasePaint = new Paint();

    public Canvas getCanvas() {
        return canvas;
    }

    public void clear() {
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), erasePaint);
    }

    public CustomView(Context context) {
        super(context);
        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        bitmap = Bitmap.createBitmap(parentWidth, parentHeight, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paint);
//        canvas.drawColor(Color.BLACK);
//        RoundRectShape roundRectShape = new RoundRectShape(new float[]{30,30,30,30,30,30,30,30},new RectF(13,13,13,13),new float[]{0,0,0,0,0,0,0,0});
//        Paint p = new Paint();
//        p.setAntiAlias(true);
//        p.setStrokeWidth(20);
//        p.setStyle(Paint.Style.STROKE);
//        p.setColor(Color.RED);
//        roundRectShape.resize(400,200);
//        OvalShape ovalShape = new OvalShape();
//        ovalShape.resize(100,200);
//        canvas.save();
//        canvas.translate(400, 400);
//        ovalShape.draw(canvas, p);
//        canvas.restore();
//        canvas.save();
//        canvas.translate(600,300);
//        roundRectShape.draw(canvas,p);
//        canvas.restore();
//        canvas.drawRoundRect(new RectF(100,100,400,300),50,20,p);
    }
}
