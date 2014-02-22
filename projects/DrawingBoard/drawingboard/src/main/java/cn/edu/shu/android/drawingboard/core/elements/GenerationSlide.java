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
    private GenerationSlideListener listener;
    private T element;
    private float startX;
    private float startY;
    private float endX;
    private float endY;

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
                                 float startX, float startY
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
                endX = startX;
                endY = startY;
                try {
                    element = ElementClass.getDeclaredConstructor(ElementClass).newInstance(app.getCurrentTool().getContent());
                    element.setGenTool(app.getCurrentTool());
                    listener.onActionDown(element, eventX, eventY, canvas);
                    draft.invalidate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                draft.clear();
                listener.onActionMove(element, eventX, eventY, canvas, startX, startY);
                draft.invalidate();
                endX = eventX;
                endY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                draft.clear();
                draft.invalidate();
                listener.onActionUp(element, eventX, eventY, startX, startY);
                element.measure(startX, startY, eventX, eventY);
                CanvasElement canvasElement = new CanvasElement(app.getContext());
                canvasElement.setContent(element);
                pc.add(canvasElement);
                break;
        }
        return true;
    }
}
