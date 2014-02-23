package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yy on 2/21/14.
 */
public class Draft extends View {
    private Bitmap buffer;
    private Canvas bufCanvas;
    private PaintCanvas pc;
    private int width;
    private int height;

    public void setPaintCanvas(PaintCanvas pc) {
        this.pc = pc;
    }

    public PaintCanvas getPaintCanvas() {
        return pc;
    }

    public Draft(Context context) {
        super(context);
    }

    public Draft(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Draft(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bufCanvas = new Canvas(buffer);
        //setBackgroundColor(Color.BLUE);
        setMeasuredDimension(width, height);
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(buffer, 0, 0, null);
    }

    public Canvas getCanvas() {
        return bufCanvas;
    }

    public void clear() {
        Paint p = new Paint();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        bufCanvas.drawRect(0, 0, width, height, p);
        //bufCanvas.drawColor(Color.BLACK);
        //bufCanvas = new Canvas(Bitmap.createBitmap(parentWidth,parentHeight, Bitmap.Config.ARGB_8888);
    }


}
