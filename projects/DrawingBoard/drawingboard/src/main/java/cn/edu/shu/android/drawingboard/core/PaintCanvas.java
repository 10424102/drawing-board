package cn.edu.shu.android.drawingboard.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.android.drawingboard.MyApplication;

/**
 * Created by yy on 1/22/14.
 */
public class PaintCanvas extends View {

    private static final MyApplication app = MyApplication.getInstance();

    private Paint pen;
    private Bitmap bmp;
    private Canvas mCanvas;

    public Canvas getCanvas() {
        return mCanvas;
    }

    private void init() {
        pen = new Paint();
        pen.setAntiAlias(true);
        pen.setColor(Color.GRAY);
        pen.setStyle(Paint.Style.STROKE);
        pen.setStrokeWidth(3);
        bmp = Bitmap.createBitmap(app.getScreenWidth(), app.getScreenHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bmp);
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
        canvas.drawBitmap(bmp, 0, 0, pen);
    }

    public void update() {
        mCanvas.drawColor(Color.RED);
        this.invalidate();
    }

    private List<CanvasElement> elementList = new ArrayList<>();

    public void addCanvasElement(CanvasElement e) {
        elementList.add(e);
        app.getMainActivity().addContentView(e, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void clear() {
        for (CanvasElement e : elementList) {
            e.setVisibility(GONE);
        }
        elementList.clear();
    }
}
