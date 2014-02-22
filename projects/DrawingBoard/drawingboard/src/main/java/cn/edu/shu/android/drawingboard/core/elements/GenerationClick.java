package cn.edu.shu.android.drawingboard.core.elements;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.view.CanvasElement;
import cn.edu.shu.android.drawingboard.view.Draft;
import cn.edu.shu.android.drawingboard.view.PaintCanvas;

/**
 * Created by yy on 2/20/14.
 */
public class GenerationClick<T extends Element> implements View.OnTouchListener {
    private MyApplication app = MyApplication.getInstance();
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
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Draft draft = (Draft) v;
        PaintCanvas pc = draft.getPaintCanvas();
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.i("yy", "Generation click action down");
                try {
                    //Create Element
                    element = ElementClass.getDeclaredConstructor(ElementClass).newInstance((T) (app.getCurrentTool().getContent()));
                    element.setGenTool(app.getCurrentTool());
                    listener.onSetElement(element, eventX, eventY);
                    element.measure(eventX, eventY, eventX, eventY);
                    CanvasElement canvasElement = new CanvasElement(app.getContext());
                    canvasElement.setContent(element);
                    pc.add(canvasElement);
                    app.getMainActivity().addContentView(canvasElement, new ViewGroup.LayoutParams(100, 100));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }
}
