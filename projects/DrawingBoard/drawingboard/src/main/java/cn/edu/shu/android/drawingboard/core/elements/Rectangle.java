package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
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
public class Rectangle extends Element implements XmlInitializable {

    public float getRectWidth() {
        return pureWidth;
    }

    public void setRectWidth(float width) {
        pureWidth = width;
    }

    public float getRectHeight() {
        return pureHeight;
    }

    public void setRectHeight(float height) {
        pureHeight = height;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Rectangle() {
        super();
    }

    public Rectangle(Rectangle x) {
        super(x);
        pureWidth = x.getRectWidth();
        pureHeight = x.getRectHeight();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Other
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void measure(float startX, float startY, float endX, float endY) {
        calculateRealSize();
        center.set((startX + endX) / 2, (startY + endY) / 2);
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        super.paint(canvas, paint);
        float top = (height - pureHeight) / 2;
        float left = (width - pureWidth) / 2;
        float bottom = top + pureHeight;
        float right = left + pureWidth;
        canvas.drawRect(left, top, right, bottom, drawPaint);
    }

    private RectF rect(float sx, float sy, float ex, float ey) {
        float cx = (sx + ex) / 2;
        float cy = (sy + ey) / 2;
        float w = Math.abs(sx - ex) / 2;
        float h = Math.abs(sy - ey) / 2;
        return new RectF(cx - w, cy - h, cx + w, cy + h);
    }

    @Override
    public void generate(View v) {
        GenerationSlide<Rectangle> gen = new GenerationSlide<>(Rectangle.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<Rectangle>() {
            @Override
            public void onActionDown(Rectangle e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint) {
            }

            @Override
            public void onActionMove(Rectangle e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawRect(rect(startX, startY, endX, endY), erasePaint);
                pc.getCanvas().drawRect(rect(startX, startY, x, y), drawPaint);
                pc.invalidate();
            }

            @Override
            public void onActionUp(Rectangle e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawRect(rect(startX, startY, endX, endY), erasePaint);
                pc.invalidate();
                e.setRectWidth(Math.abs(startX - x));
                e.setRectHeight(Math.abs(startY - y));
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
