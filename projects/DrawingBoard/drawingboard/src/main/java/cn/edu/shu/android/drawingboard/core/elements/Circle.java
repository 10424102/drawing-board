package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;
import cn.edu.shu.android.drawingboard.core.XmlInitializable;
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
        pureWidth = radius * 2;
        pureHeight = radius * 2;
        calculateRealSize();
        center.set(startX, startY);
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        super.paint(canvas, paint);
        canvas.drawCircle(width / 2, height / 2, radius, drawPaint);
    }

    private float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public void generate(View v) {
        GenerationSlide<Circle> gen = new GenerationSlide<>(Circle.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<Circle>() {
            @Override
            public void onActionDown(Circle e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint) {
            }

            @Override
            public void onActionMove(Circle e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawCircle(startX, startY, distance(startX, startY, endX, endY), erasePaint);
                pc.getCanvas().drawCircle(startX, startY, distance(startX, startY, x, y), drawPaint);
                pc.invalidate();
            }

            @Override
            public void onActionUp(Circle e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawCircle(startX, startY, distance(startX, startY, endX, endY), erasePaint);
                pc.invalidate();
                e.setRadius(distance(startX, startY, x, y));
            }
        });
        v.setOnTouchListener(gen);
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
