package cn.edu.shu.android.drawingboard.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yy on 1/22/14.
 */
public class PaintCanvas extends View {

    private Paint pen;
    private Bitmap bmp;
    private Canvas c;

    private void init(){
        pen = new Paint();
        pen.setAntiAlias(true);
        pen.setColor(Color.GRAY);
        pen.setStyle(Paint.Style.STROKE);
        pen.setStrokeWidth(3);
        bmp = Bitmap.createBitmap(320,480,Bitmap.Config.ARGB_8888);
        c = new Canvas(bmp);
        c.drawCircle(50,50,40,pen);
    }


    public PaintCanvas(Context context) {
        super(context);
        init();
    }

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp,0,0,pen);
    }

    public Canvas getCanvas() {
        return c;
    }

    public void update(){
        c.drawColor(Color.RED);
        this.invalidate();
    }

}
