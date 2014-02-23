package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.core.elements.Element;

/**
 * Created by yy on 2/21/14.
 */
public class CanvasElement extends View {
    private PaintCanvas pc;
    private Element content;
    private Bitmap buffer;
    private Canvas bufCanvas;
    private float width;
    private float height;
    private boolean selected = false;

    public void setContent(Element e) {
        content = e;
        float paintSize = (float) Math.ceil(e.getPaint().getStrokeWidth());
        width = e.getWidth() + paintSize;
        height = e.getHeight() + paintSize;
        buffer = Bitmap.createBitmap((int) (width + 0.5), (int) (height + 0.5), Bitmap.Config.ARGB_8888);
        bufCanvas = new Canvas(buffer);
        content.paint(bufCanvas);
    }

    private void init() {
        final CanvasElement element = this;
        setOnTouchListener(new OnTouchListener() {
            private float px;
            private float py;
            private boolean moved;
            private boolean justChanged;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CanvasElement e = (CanvasElement) v;
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    if (content.inside(event.getX(), event.getY())) {
                        if (!selected) {
                            selected = true;
                            select();
                            justChanged = true;
                        } else {
                            justChanged = false;
                        }
                        px = event.getRawX();
                        py = event.getRawY();
                        moved = false;
                    }
                } else if (action == MotionEvent.ACTION_MOVE) {
                    moved = true;
                    if (selected) {
                        float dx = event.getRawX() - px;
                        float dy = event.getRawY() - py;
                        v.setX(v.getX() + dx);
                        v.setY(v.getY() + dy);
                        px = event.getRawX();
                        py = event.getRawY();
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    if (selected && !moved && !justChanged) {
                        selected = false;
                        unselect();
                        justChanged = true;
                    } else {
                        justChanged = false;
                    }
                }
                Log.i("yy", Boolean.toString(selected) + "," + Boolean.toString(moved) + "," + Boolean.toString(justChanged));
                return true;
            }
        });
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        //int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) (width + 0.5), (int) (height + 0.5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(buffer, 0, 0, null);
    }

    public Canvas getCanvas() {
        return bufCanvas;
    }

    public void setPaintCanvas(PaintCanvas pc) {
        this.pc = pc;
    }

    public PaintCanvas getPaintCanvas() {
        return pc;
    }

    public void select() {
        selected = true;
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        bufCanvas.drawRect(1, 1, width - 1, height - 1, p);
        pc.addSelectedCanvasElement(this);
        invalidate();
    }

    public void unselect() {
        selected = false;
        Paint p = new Paint();
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        p.setStyle(Paint.Style.STROKE);
        bufCanvas.drawRect(1, 1, width - 1, height - 1, p);
        pc.removeSelectedCanvasElement(this);
        invalidate();
    }
}
