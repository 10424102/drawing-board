package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.core.element.Gaia;

/**
 * Created by yy on 2/26/14.
 */
public class CanvasElement extends View {
    public static final int SELECT_FLAG = 2;
    public static final int TRANSLATE_FLAG = 4;
    public static final int ROTATE_FLAG = 8;
    public static final int SCALE_FLAG = 16;

    private PaintCanvas paintCanvas;
    private int flags = 0;
    private Gaia gaia;
    private boolean selected = false;

    public CanvasElement(Context context, Gaia gaia) {
        super(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((flags | ~SELECT_FLAG) == -1) {
                    if (selected) {
                        unselect();
                    } else {
                        select();
                    }
                }
            }
        });
        this.gaia = gaia;
        setX(gaia.getLeft());
        setY(gaia.getTop());
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    private void select() {
        selected = true;
        setBackgroundColor(Color.LTGRAY);
        if (paintCanvas == null) {
            paintCanvas = ((Artwork) getParent()).getPaintCanvas();
        }
        paintCanvas.getSelectedElements().add(this);
        paintCanvas.mirror();
    }

    private void unselect() {
        selected = false;
        setBackgroundColor(Color.TRANSPARENT);
        paintCanvas.getSelectedElements().remove(this);
    }

    public Gaia getGaia() {
        return gaia;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) gaia.getWidth(), (int) gaia.getHeight());
        //setMeasuredDimension(400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.argb(20, 100, 100, 100));
        gaia.paint(canvas);
    }
}
