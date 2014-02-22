package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;

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
public class Circle extends Element implements XmlInitializable {
    private float radius;

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Circle() {
        super();
    }

    public Circle(Circle x) {
        super(x);
        radius = x.getRadius();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Other
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void measure(float startX, float startY, float endX, float endY) {
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawCircle(center.x, center.y, radius, getPaint());
    }

    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void generate() {
        GenerationSlide<Circle> gen = new GenerationSlide<>(Circle.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<Circle>() {
            @Override
            public void onActionDown(Circle e, float x, float y, Canvas canvas) {
                e.setCenter(x, y);
                canvas.drawPoint(x, y, e.getPaint());
            }

            @Override
            public void onActionMove(Circle e, float x, float y, Canvas canvas, float startX, float startY) {
                canvas.drawCircle(startX, startY, distance(startX, startY, x, y), e.getPaint());
            }

            @Override
            public void onActionUp(Circle e, float x, float y, float startX, float startY) {
                e.setRadius(distance(startX, startY, x, y));
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
