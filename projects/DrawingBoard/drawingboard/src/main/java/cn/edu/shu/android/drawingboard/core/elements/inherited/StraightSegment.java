package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.GenerationSlide;
import cn.edu.shu.android.drawingboard.core.elements.Position;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public StraightSegment() {
        super();
    }

    public StraightSegment(StraightSegment x) {
        super(x);
        start = new Position(x.getStart());
        end = new Position(x.getEnd());
    }

    @Override
    public Position measure(float startX, float startY, float endX, float endY) {
        float paintStrokeWidth = paint.getStrokeWidth();
        width = Math.abs(startX - endX) + paintStrokeWidth + PADDING;
        height = Math.abs(startY - endY) + paintStrokeWidth + PADDING;
        centerX = width / 2;
        centerY = height / 2;
        Position leftTop = new Position((startX + endX) / 2 - centerX, (startY + endY) / 2 - centerY);
        start.offset(-leftTop.x, -leftTop.y);
        end.offset(-leftTop.x, -leftTop.y);
        return leftTop;
    }


    @Override
    public void paint(Canvas canvas) {
        canvas.drawLine(start.x, start.y, end.x, end.y, paint);
    }

    @Override
    public void generate() {
        GenerationSlide<StraightSegment> gen = new GenerationSlide<>(StraightSegment.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<StraightSegment>() {
            @Override
            public void onActionDown(StraightSegment e, float x, float y, Canvas canvas) {
                canvas.drawPoint(x, y, e.getPaint());
            }

            @Override
            public void onActionMove(StraightSegment e, float x, float y, Canvas canvas, float startX, float startY, float prevX, float prevY) {
                canvas.drawLine(startX, startY, x, y, e.getPaint());
            }

            @Override
            public void onActionUp(StraightSegment e, float x, float y, float startX, float startY) {
                e.setStart(startX, startY);
                e.setEnd(x, y);
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
