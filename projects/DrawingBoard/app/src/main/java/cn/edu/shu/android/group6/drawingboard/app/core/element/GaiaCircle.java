package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.core.tool.Tool;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Draft;
import cn.edu.shu.android.group6.drawingboard.app.util.MathUtil;

/**
 * Created by yy on 2/27/14.
 */
public class GaiaCircle extends Gaia {

    public GaiaCircle(GaiaCircle g) {
        super(g);
        this.radius = g.radius;
    }

    private float radius;
    private static final View.OnTouchListener gen = new View.OnTouchListener() {
        float sx;
        float sy;
        Draft draft;
        GaiaCircle template;
        Canvas canvas;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    draft = app.getPaintCanvas().getDraft();
                    template = (GaiaCircle) app.getCurrentTool().getGenerator();
                    canvas = draft.getCanvas();
                    sx = event.getX();
                    sy = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    draft.clear();
                    canvas.drawCircle(sx, sy, MathUtil.distance(sx, sy, event.getX(), event.getY()), app.getPaint());
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    draft.clear();
                    draft.invalidate();
                    GaiaCircle gaia = new GaiaCircle(template);
                    gaia.set(app.getPaint(), MathUtil.distance(sx, sy, event.getX(), event.getY()), sx, sy);
                    CanvasElement element = new CanvasElement(app.getContext(), gaia);
                    element.setFlags(CanvasElement.SELECT_FLAG);
                    app.getPaintCanvas().getArtwork().addView(element);
                    break;
            }
            return true;
        }
    };

    public void set(Paint p, float r, float centerX, float centerY) {
        setPaint(p);
        radius = r;
        setSize(r * 2 + paint.getStrokeWidth() + 2, r * 2 + paint.getStrokeWidth() + 2);
        setLeftTop(centerX - width / 2, centerY - height / 2);
    }

    public GaiaCircle(Tool genTool) {
        super(genTool);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(gen);
    }
}
