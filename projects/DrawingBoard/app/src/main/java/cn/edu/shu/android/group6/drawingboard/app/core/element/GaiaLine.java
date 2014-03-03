package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.core.tool.Tool;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Draft;

/**
 * Created by yy on 3/1/14.
 */
public class GaiaLine extends Gaia {
    public GaiaLine(GaiaLine g) {
        super(g);
        this.sx = g.sx;
        this.sy = g.sy;
        this.ex = g.ex;
        this.ey = g.ey;
    }

    private static final View.OnTouchListener gen = new View.OnTouchListener() {
        Draft draft;
        Canvas canvas;
        GaiaLine template;
        float sx;
        float sy;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    draft = app.getPaintCanvas().getDraft();
                    canvas = draft.getCanvas();
                    template = (GaiaLine) app.getCurrentTool().getGenerator();
                    sx = event.getX();
                    sy = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    draft.clear();
                    draft.getCanvas().drawLine(sx, sy, event.getX(), event.getY(), app.getPaint());
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    draft.clear();
                    draft.invalidate();
                    GaiaLine gaia = new GaiaLine(template);
                    gaia.set(app.getPaint(), sx, sy, event.getX(), event.getY());
                    CanvasElement element = new CanvasElement(app.getContext(), gaia);
                    app.getPaintCanvas().getArtwork().addView(element);
                    break;
            }
            return true;
        }
    };
    private float sx;
    private float sy;
    private float ex;
    private float ey;

    public void set(Paint p, float sx, float sy, float ex, float ey) {
        setPaint(p);
        float w = Math.abs(sx - ex);
        float h = Math.abs(sy - ey);
        float cx = (sx + ex) / 2;
        float cy = (sy + ey) / 2;
        setSize(w + paint.getStrokeWidth() + 2, h + paint.getStrokeWidth() + 2);
        setLeftTop(cx - width / 2, cy - height / 2);
        this.sx = sx - left;
        this.ex = ex - left;
        this.sy = sy - top;
        this.ey = ey - top;
    }

    public GaiaLine(Tool genTool) {
        super(genTool);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawLine(sx, sy, ex, ey, paint);
    }

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(gen);
    }
}
