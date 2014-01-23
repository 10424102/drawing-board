package cn.edu.shu.android.drawingboard.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by yy on 1/22/14.
 */
public class PaintCanvas extends View {
    private Canvas c;

    public PaintCanvas(Context context) {
        super(context);
        Bitmap bmp = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        c = new Canvas(bmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas = c;
    }

    public Canvas getCanvas() {
        return c;
    }
}
