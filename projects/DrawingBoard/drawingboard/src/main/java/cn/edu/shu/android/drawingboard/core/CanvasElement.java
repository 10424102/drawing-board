package cn.edu.shu.android.drawingboard.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.elements.Element;

/**
 * Created by yy on 2/4/14.
 */
public class CanvasElement extends View implements View.OnTouchListener {
    private static final MyApplication app = MyApplication.getInstance();
    private Element mContent;

    public void setContent(Element p) {
        mContent = p;
        mBitmap = Bitmap.createBitmap(mContent.getWidthInt(), mContent.getHeightInt(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
//    private ViewGroup.MarginLayoutParams lp;
//    private float mLeft = 0;
//    public void setLeft(float left){
//        mLeft = left;
//        lp.setMargins((int)mLeft,(int)mTop,0,0);
//    }
//    private float mTop = 0;
//    public void setTop(float top){
//        mTop = top;
//        lp.setMargins((int)mLeft,(int)mTop,0,0);
//    }
//    private boolean selected = false;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private void init() {
        setOnTouchListener(this);
        mPaint = new Paint();
        //lp = (ViewGroup.MarginLayoutParams)getLayoutParams();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mContent.getWidthInt() != mBitmap.getWidth() || mContent.getHeightInt() != mBitmap.getHeight()) {
            mBitmap = Bitmap.createBitmap(mContent.getWidthInt(), mContent.getHeightInt(), Bitmap.Config.ARGB_8888);
            requestLayout();
        }
        mContent.paint(mCanvas, null);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        setX(mContent.getCenter().x - mContent.getWidth() / 2);
        setY(mContent.getCenter().y - mContent.getHeight() / 2);
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
                if (event.getX() <= mContent.getWidth() && event.getY() <= mContent.getHeight()) {
                    mCanvas.drawColor(Color.argb(0x11, 0x11, 0x22, 0x33));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }
}
