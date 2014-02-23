package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.Position;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

/**
 * Created by yy on 2/22/14.
 */
public class Selector extends Element implements XmlInitializable {
    @Override
    public void paint(Canvas canvas) {

    }

    @Override
    public void generate() {
        app.setPaintCanvasOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public boolean inside(float x, float y) {
        return false;
    }

    @Override
    public Position measure(float startX, float startY, float endX, float endY) {
        return null;
    }

    @Override
    public boolean xmlParse(Block block) throws XmlParserException {
        return false;
    }
}
