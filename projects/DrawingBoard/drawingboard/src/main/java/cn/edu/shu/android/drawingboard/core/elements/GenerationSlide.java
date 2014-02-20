package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.CanvasElement;
import cn.edu.shu.android.drawingboard.core.PaintCanvas;

/**
 * Created by yy on 2/20/14.
 */
public class GenerationSlide<T extends Element> implements View.OnTouchListener {
    private MyApplication app = MyApplication.getInstance();
    private PaintCanvas pc = app.getPaintCanvas();
    private Class<T> ElementClass;
    private GenerationSlideListener listener;
    private Paint drawPaint = app.getCurrentTool().getDrawPaint();
    private Paint erasePaint = app.getCurrentTool().getErasePaint();
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
                                 PaintCanvas pc,
                                 Paint drawPaint, Paint erasePaint);

        public void onActionMove(T e,
                                 float x, float y,
                                 PaintCanvas pc,
                                 Paint drawPaint, Paint erasePaint,
                                 float startX, float startY,
                                 float endX, float endY);

        public void onActionUp(T e,
                               float x, float y,
                               PaintCanvas pc,
                               Paint drawPaint, Paint erasePaint,
                               float startX, float startY,
                               float endX, float endY);
    }

    public GenerationSlide(Class<T> clazz) {
        ElementClass = clazz;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
                    listener.onActionDown(element, eventX, eventY, pc, drawPaint, erasePaint);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                listener.onActionMove(element, eventX, eventY, pc, drawPaint, erasePaint, startX, startY, endX, endY);
                endX = eventX;
                endY = eventY;
                break;
            case MotionEvent.ACTION_UP:
                listener.onActionUp(element, eventX, eventY, pc, drawPaint, erasePaint, startX, startY, endX, endY);
                element.measure(startX, startY, eventX, eventY);
                CanvasElement canvasElement = new CanvasElement(app.getContext());
                canvasElement.setContent(element);
                pc.addCanvasElement(canvasElement);
                break;
        }
        return true;
    }
}
