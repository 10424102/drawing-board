package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

import java.util.Date;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.ControlArc;
import cn.edu.shu.android.group6.drawingboard.app.core.view.ControlPoint;
import cn.edu.shu.android.group6.drawingboard.app.core.view.PaintCanvas;

/**
 * Created by yy on 3/1/14.
 */
public class TranslateAnimationTool implements Generable {
    private static final App app = App.getInstance();
    private static final PaintCanvas paintCanvas = app.getPaintCanvas();

    @Override
    public void generate() {
        List<CanvasElement> elements = paintCanvas.getSelectedElements();
        if (elements.isEmpty()) return;
        CanvasElement selected = elements.get(0);
        CanvasElement copy = selected.copy();
        selected.setVisibility(View.INVISIBLE);
        paintCanvas.getMirror().shadeOn();
        paintCanvas.getMirror().addView(copy);
        copy.setBackgroundColor(Color.WHITE);
        ControlPoint controlPoint = new ControlPoint(app.getContext(), copy);
        controlPoint.setX(copy.getGaia().getWidth() / 2 + copy.getX() - ControlPoint.SIZE / 2);
        controlPoint.setY(copy.getGaia().getHeight() / 2 + copy.getY() - ControlPoint.SIZE / 2);
        controlPoint.setOnTouchListener(new View.OnTouchListener() {
            float sx;
            float sy;
            Date startTime;
            Date endTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float pcx = event.getX() + v.getX();
                float pcy = event.getY() + v.getY();
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        startTime = new Date();
                        sx = v.getX() + ControlPoint.SIZE / 2;
                        sy = v.getY() + ControlPoint.SIZE / 2;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setOnTouchListener(null);
                        ControlPoint cp = (ControlPoint) v;
                        CanvasElement element = cp.getElement();
                        float deltaX = pcx - sx;
                        float deltaY = pcy - sy;
                        TranslateAnimation translateAnimation = new TranslateAnimation(0, deltaX, 0, deltaY);
                        endTime = new Date();
                        translateAnimation.setDuration(endTime.getTime() - startTime.getTime());
                        translateAnimation.setFillAfter(true);
                        //element.setAnimation(translateAnimation);
                        element.startAnimation(translateAnimation);
                        ControlPoint controlPoint = new ControlPoint(app.getContext(), element);
                        controlPoint.setX(pcx - ControlPoint.SIZE / 2);
                        controlPoint.setY(pcy - ControlPoint.SIZE / 2);
                        app.getPaintCanvas().getMirror().addView(controlPoint);
                        ControlArc controlArc = new ControlArc(app.getContext());
                        controlArc.setFrom(cp);
                        controlArc.setTo(controlPoint);
                        app.getPaintCanvas().getMirror().addView(controlArc);
                        break;
                }
                return true;
            }
        });
        paintCanvas.getMirror().addView(controlPoint);

    }
}
