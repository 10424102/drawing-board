package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.GenerationSlide;
import cn.edu.shu.android.drawingboard.core.elements.Position;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlInitializer;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

/**
 * Created by yy on 2/6/14.
 */
public class Rectangle extends Element implements XmlInitializable {

    private float rectWidth;
    private float rectHeight;

    public float getRectWidth() {
        return rectWidth;
    }

    public void setRectWidth(float rectWidth) {
        this.rectWidth = rectWidth;
    }

    public float getRectHeight() {
        return rectHeight;
    }

    public void setRectHeight(float rectHeight) {
        this.rectHeight = rectHeight;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Rectangle() {
        super();
    }

    public Rectangle(Rectangle x) {
        super(x);
        rectWidth = x.getRectWidth();
        rectHeight = x.getRectHeight();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Other
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Position measure(float startX, float startY, float endX, float endY) {
        float paintStrokeWidth = paint.getStrokeWidth();
        width = rectWidth + paintStrokeWidth + PADDING;
        height = rectHeight + paintStrokeWidth + PADDING;
        centerX = width / 2;
        centerY = height / 2;
        return new Position((startX + endX) / 2 - centerX, (startY + endY) / 2 - centerY);
    }

    @Override
    public void paint(Canvas canvas) {
        float left = (width - rectWidth) / 2;
        float top = (height - rectHeight) / 2;
        float right = left + rectWidth;
        float bottom = top + rectHeight;
        canvas.drawRect(left, top, right, bottom, paint);
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
            public void onActionMove(Rectangle e, float x, float y, Canvas canvas, float startX, float startY, float prevX, float prevY) {
                canvas.drawRect(rect(startX, startY, x, y), e.getPaint());
            }

            @Override
            public void onActionUp(Rectangle e, float x, float y, float startX, float startY) {
                RectF r = rect(startX, startY, x, y);
                e.setRectWidth(r.width());
                e.setRectHeight(r.height());
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

    @Override
    public boolean inside(float x, float y) {
        return true;
    }
}
