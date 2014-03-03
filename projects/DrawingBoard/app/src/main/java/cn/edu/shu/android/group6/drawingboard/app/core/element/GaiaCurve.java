package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.core.tool.Tool;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Draft;

/**
 * Created by yy on 3/1/14.
 */
public class GaiaCurve extends Gaia {
    public GaiaCurve(GaiaCurve g) {
        super(g);
        this.path = new Path(g.path);
    }

    private static final View.OnTouchListener gen = new View.OnTouchListener() {
        final Path path = new Path();
        Draft draft;
        Canvas canvas;
        GaiaCurve template;
        float px;
        float py;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    draft = app.getPaintCanvas().getDraft();
                    canvas = draft.getCanvas();
                    template = (GaiaCurve) app.getCurrentTool().getGenerator();
                    path.reset();
                    path.moveTo(event.getX(), event.getY());
                    px = event.getX();
                    py = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(event.getX(), event.getY());
                    canvas.drawLine(px, py, event.getX(), event.getY(), app.getPaint());
                    px = event.getX();
                    py = event.getY();
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    draft.clear();
                    draft.invalidate();
                    GaiaCurve gaia = new GaiaCurve(template);
                    gaia.set(app.getPaint(), path);
                    CanvasElement element = new CanvasElement(app.getContext(), gaia);
                    app.getPaintCanvas().getArtwork().addView(element);
                    break;
            }
            return true;
        }
    };
    private Path path = new Path();

    public void set(Paint p, Path path) {
        setPaint(p);
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        setSize(bounds.width() + paint.getStrokeWidth() * 2 + 2, bounds.height() + paint.getStrokeWidth() * 2 + 2);
        setLeftTop(bounds.centerX() - width / 2, bounds.centerY() - height / 2);
        path.offset(-left, -top, this.path);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public GaiaCurve(Tool genTool) {
        super(genTool);
    }

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(gen);
    }
}
