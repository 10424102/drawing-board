package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Draft;

/**
 * Created by yy on 3/1/14.
 */
public class GaiaSmoothCurve extends Gaia {
    private static class Point {
        public float x;
        public float y;
        public float dx;
        public float dy;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final View.OnTouchListener gen = new View.OnTouchListener() {
        final Path path = new Path();
        final Draft draft = app.getPaintCanvas().getDraft();
        final List<Point> points = new ArrayList<>();

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    path.reset();
                    path.moveTo(event.getX(), event.getY());
                    points.clear();
                    points.add(new Point(event.getX(), event.getY()));
                    break;
                case MotionEvent.ACTION_MOVE:
                    draft.clear();
                    path.lineTo(event.getX(), event.getY());
                    points.add(new Point(event.getX(), event.getY()));
                    draft.getCanvas().drawPath(path, app.getPaint());
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    draft.clear();
                    draft.invalidate();
                    GaiaSmoothCurve gaia = new GaiaSmoothCurve();
                    gaia.set(app.getPaint(), points);
                    CanvasElement element = new CanvasElement(app.getContext(), gaia);
                    app.getPaintCanvas().getArtwork().addView(element);
                    break;
            }
            return true;
        }
    };
    private Path path = new Path();

    private Path makeSmoothPath(List<Point> points) {
        Path path = new Path();

        if (points.size() > 1) {
            for (int i = points.size() - 2; i < points.size(); i++) {
                if (i >= 0) {
                    Point point = points.get(i);

                    if (i == 0) {
                        Point next = points.get(i + 1);
                        point.dx = ((next.x - point.x) / 3);
                        point.dy = ((next.y - point.y) / 3);
                    } else if (i == points.size() - 1) {
                        Point prev = points.get(i - 1);
                        point.dx = ((point.x - prev.x) / 3);
                        point.dy = ((point.y - prev.y) / 3);
                    } else {
                        Point next = points.get(i + 1);
                        Point prev = points.get(i - 1);
                        point.dx = ((next.x - prev.x) / 3);
                        point.dy = ((next.y - prev.y) / 3);
                    }
                }
            }
        }

        boolean first = true;
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else {
                Point prev = points.get(i - 1);
                path.cubicTo(prev.x + prev.dx, prev.y + prev.dy, point.x - point.dx, point.y - point.dy, point.x, point.y);
            }
        }
        return path;
    }

    private Path makeSmoothPath2(List<Point> points) {
        Path path = new Path();
        boolean first = true;
        for (int i = 0; i < points.size(); i += 2) {
            Point point = points.get(i);
            if (first) {
                first = false;
                path.moveTo(point.x, point.y);
            } else if (i < points.size() - 1) {
                Point next = points.get(i + 1);
                path.quadTo(point.x, point.y, next.x, next.y);
            } else {
                path.lineTo(point.x, point.y);
            }
        }
        return path;
    }

    public void set(Paint p, List<Point> points) {
        setPaint(p);
        Path path = makeSmoothPath2(points);
        RectF bounds = new RectF();
        path.computeBounds(bounds, true);
        setSize(bounds.width() + paint.getStrokeWidth() + 2, bounds.height() + paint.getStrokeWidth() + 2);
        setLeftTop(bounds.centerX() - width / 2, bounds.centerY() - height / 2);
        path.offset(-left, -top, this.path);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(gen);
    }
}
