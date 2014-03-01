package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Draft;

/**
 * Created by yy on 3/1/14.
 */
public class GaiaRectangle extends Gaia {
    private RectF rect;

    private static final View.OnTouchListener gen = new View.OnTouchListener() {
        float sx;
        float sy;
        final Draft draft = app.getPaintCanvas().getDraft();

        private RectF f(float x1, float y1, float x2, float y2) {
            float cx = (x1 + x2) / 2;
            float cy = (y1 + y2) / 2;
            float w = Math.abs(x1 - x2) / 2;
            float h = Math.abs(y1 - y2) / 2;
            return new RectF(cx - w, cy - h, cx + w, cy + h);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    sx = event.getX();
                    sy = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    draft.clear();
                    draft.getCanvas().drawRect(f(sx, sy, event.getX(), event.getY()), app.getPaint());
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    draft.clear();
                    draft.invalidate();
                    GaiaRectangle gaia = new GaiaRectangle();
                    gaia.set(app.getPaint(), f(sx, sy, event.getX(), event.getY()));
                    CanvasElement element = new CanvasElement(app.getContext(), gaia);
                    app.getPaintCanvas().getArtwork().addView(element);
                    break;
            }
            return true;
        }
    };

    public void set(Paint p, RectF r) {
        setPaint(p);
        setSize(r.width() + paint.getStrokeWidth() + 2, r.height() + paint.getStrokeWidth() + 2);
        setLeftTop(r.centerX() - width / 2, r.centerY() - height / 2);
        r.offset(-left, -top);
        rect = new RectF(r);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawRect(rect, paint);
        //canvas.drawRoundRect(rect,30,30,paint);
    }

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(gen);
    }
}
