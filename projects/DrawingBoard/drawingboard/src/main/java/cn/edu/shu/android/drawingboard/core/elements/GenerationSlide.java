package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.view.CanvasElement;
import cn.edu.shu.android.drawingboard.view.Draft;
import cn.edu.shu.android.drawingboard.view.PaintCanvas;

/**
 * Created by yy on 2/20/14.
 */
public class GenerationSlide<T extends Element> implements View.OnTouchListener {
    private MyApplication app = MyApplication.getInstance();
    private Class<T> ElementClass;
    private GenerationSlideListener listener = new GenerationSlideListener() {
        @Override
        public void onActionDown(Element e, float x, float y, Canvas canvas) {

        }

        @Override
        public void onActionMove(Element e, float x, float y, Canvas canvas, float startX, float startY, float prevX, float prevY) {

        }

        @Override
        public void onActionUp(Element e, float x, float y, float startX, float startY) {

        }
    };
    private T element;
    private float startX;
    private float startY;
    private float prevX;
    private float prevY;

    public void setGenerationSlideListener(GenerationSlideListener l) {
        listener = l;
    }

    public interface GenerationSlideListener<T extends Element> {
        public void onActionDown(T e,
                                 float x, float y,
                                 Canvas canvas
        );

        public void onActionMove(T e,
                                 float x, float y,
                                 Canvas canvas,
                                 float startX, float startY,
                                 float prevX, float prevY
        );

        public void onActionUp(T e,
                               float x, float y,
                               float startX, float startY
        );
    }

    public GenerationSlide(Class<T> clazz) {
        ElementClass = clazz;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Draft draft = (Draft) v;
        Canvas canvas = draft.getCanvas();
        PaintCanvas pc = draft.getPaintCanvas();
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = eventX;
                startY = eventY;
                prevX = startX;
                prevY = startY;
                try {
                    element = ElementClass.getDeclaredConstructor(ElementClass).newInstance(app.getCurrentTool().getContent());
                    element.setGenTool(app.getCurrentTool());
                    //element.setPaint(app.getCurrentPaint());
                    listener.onActionDown(element, eventX, eventY, canvas);
                    draft.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                draft.clear();
                listener.onActionMove(element, eventX, eventY, canvas, startX, startY, prevX, prevY);
                draft.invalidate();
                prevX = eventX;
                prevY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                draft.clear();
                draft.invalidate();
                listener.onActionUp(element, eventX, eventY, startX, startY);
                Position leftTop = element.measure(startX, startY, eventX, eventY);
                CanvasElement canvasElement = new CanvasElement(app.getContext());
                canvasElement.setX(leftTop.x);
                canvasElement.setY(leftTop.y);
                canvasElement.setContent(element);
                pc.add(canvasElement);
                break;
        }
        return true;
    }
}
