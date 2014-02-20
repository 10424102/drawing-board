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
 * Created by yy on 2/5/14.
 */
public class StraightSegment extends Element implements XmlInitializable {

    private Position start;
    private Position end;

    public void setStart(float x, float y) {
        start = new Position(x, y);
    }

    public Position getStart() {
        return start;
    }

    public void setEnd(float x, float y) {
        end = new Position(x, y);
    }

    public Position getEnd() {
        return end;
    }

    //Constructor
    public StraightSegment() {
        super();
    }

    public StraightSegment(StraightSegment x) {
        super(x);
        start = new Position(x.getStart());
        end = new Position(x.getEnd());
    }

    public void measure(float startX, float startY, float endX, float endY) {
        pureWidth = Math.abs(start.x - end.x);
        pureHeight = Math.abs(start.y - end.y);
        calculateRealSize();
        float dx = (start.x + end.x - width) / 2;
        float dy = (start.y + end.y - height) / 2;
        start.offset(-dx, -dy);
        end.offset(-dx, -dy);
        center.set((startX + endX) / 2, (startY + endY) / 2);
    }


    @Override
    public void paint(Canvas canvas, Paint paint) {
        super.paint(canvas, paint);
        canvas.drawLine(start.x, start.y, end.x, end.y, drawPaint);
    }

    @Override
    public void generate(View v) {
        GenerationSlide<StraightSegment> gen = new GenerationSlide<>(StraightSegment.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<StraightSegment>() {
            @Override
            public void onActionDown(StraightSegment e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint) {
            }

            @Override
            public void onActionMove(StraightSegment e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawLine(startX, startY, endX, endY, erasePaint);
                pc.getCanvas().drawLine(startX, startY, x, y, drawPaint);
                pc.invalidate();
            }

            @Override
            public void onActionUp(StraightSegment e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawLine(startX, startY, endX, endY, erasePaint);
                e.setStart(startX, startY);
                e.setEnd(x, y);
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
