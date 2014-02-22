package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.GenerationSlide;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlInitializer;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

/**
 * Created by yy on 2/6/14.
 */
public class Rectangle extends Element implements XmlInitializable {

    private float left;
    private float top;
    private float right;
    private float bottom;

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setSize(float l, float t, float r, float b) {
        left = l;
        top = t;
        right = r;
        bottom = b;
    }

    public void setSize(RectF rect) {
        left = rect.left;
        top = rect.top;
        right = rect.right;
        bottom = rect.bottom;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Rectangle() {
        super();
    }

    public Rectangle(Rectangle x) {
        super(x);
        left = x.getLeft();
        top = x.getTop();
        right = x.getRight();
        bottom = x.getBottom();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Other
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void measure(float startX, float startY, float endX, float endY) {
        center.set((startX + endX) / 2, (startY + endY) / 2);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawRect(left, top, right, bottom, getPaint());
    }

    private RectF rect(float sx, float sy, float ex, float ey) {
        float cx = (sx + ex) / 2;
        float cy = (sy + ey) / 2;
        float w = Math.abs(sx - ex) / 2;
        float h = Math.abs(sy - ey) / 2;
        return new RectF(cx - w, cy - h, cx + w, cy + h);
    }

    @Override
    public void generate() {
        GenerationSlide<Rectangle> gen = new GenerationSlide<>(Rectangle.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<Rectangle>() {
            @Override
            public void onActionDown(Rectangle e, float x, float y, Canvas canvas) {

            }

            @Override
            public void onActionMove(Rectangle e, float x, float y, Canvas canvas, float startX, float startY) {
                canvas.drawRect(rect(startX, startY, x, y), e.getPaint());
            }

            @Override
            public void onActionUp(Rectangle e, float x, float y, float startX, float startY) {
                e.setSize(rect(startX, startY, x, y));
            }
        });
        app.setPaintCanvasOnTouchListener(gen);
    }

    @Override
    public boolean xmlParse(Block block) throws XmlParserException {
        for (Iterator i = block.blockIterator(); i.hasNext(); ) {
            Block b = (Block) i.next();
            switch (b.getName().toLowerCase()) {
                case "paint":
                    setPaint(XmlInitializer.getPaint(b));
                    break;
                case "center":
                    break;
            }
        }
        return true;
    }
}
