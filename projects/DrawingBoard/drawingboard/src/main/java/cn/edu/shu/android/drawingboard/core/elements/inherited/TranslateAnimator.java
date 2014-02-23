package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.Position;
import cn.edu.shu.android.drawingboard.view.PaintCanvas;

/**
 * Created by yy on 2/23/14.
 */
public class TranslateAnimator extends Element {
    @Override
    public Position measure(float startX, float startY, float endX, float endY) {
        return null;
    }

    @Override
    public boolean inside(float x, float y) {
        return false;
    }

    @Override
    public void generate() {
        PaintCanvas pc = app.getCurrentPaintCanvas();
        pc.clearSelectedCanvasElement();
        pc.setMultiSelect(false);
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MyApplication.MSG_ELEMENT_SELECT) {
                }
                return false;
            }
        });
        View.OnTouchListener gen = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        };
        app.setPaintCanvasOnTouchListener(gen);
    }

    @Override
    public void paint(Canvas canvas) {

    }
}
