package cn.edu.shu.android.drawingboard.core.elements;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.CanvasElement;
import cn.edu.shu.android.drawingboard.core.PaintCanvas;

/**
 * Created by yy on 2/20/14.
 */
public class GenerationClick<T extends Element> implements View.OnTouchListener {
    private MyApplication app;
    private PaintCanvas pc;
    private Class<T> ElementClass;
    private GenerationClickListener listener;
    private T element;

    public void setGenerationClickListener(GenerationClickListener l) {
        listener = l;
    }

    public interface GenerationClickListener<T extends Element> {
        public void onSetElement(T e, float clickX, float clickY);
    }

    public GenerationClick(Class<T> clazz) {
        ElementClass = clazz;
        app = MyApplication.getInstance();
        pc = app.getPaintCanvas();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("yy", "Generation click action down");
                try {
                    //Create Element
                    element = ElementClass.getDeclaredConstructor(ElementClass).newInstance((T) (app.getCurrentTool().getContent()));
                    element.setGenTool(app.getCurrentTool());
                    listener.onSetElement(element, eventX, eventY);
                    element.measure(eventX, eventY, eventX, eventY);

                    //Create CanvasElement
                    CanvasElement canvasElement = new CanvasElement(app.getContext());
                    canvasElement.setContent(element);

                    //Add CanvasElement to PaintCanvas
                    pc.addCanvasElement(canvasElement);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }
}
