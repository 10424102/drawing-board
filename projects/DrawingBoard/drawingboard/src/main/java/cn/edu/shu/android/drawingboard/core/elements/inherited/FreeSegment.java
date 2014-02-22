package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;
import android.graphics.Path;
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
public class FreeSegment extends Element implements XmlInitializable {

    private Path path = new Path();

    public Path getPath() {
        return path;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public FreeSegment() {
        super();
    }

    public FreeSegment(FreeSegment x) {
        super(x);
        path = new Path(x.getPath());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Other
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Position measure(float startX, float startY, float endX, float endY) {
        float paintStrokeWidth = paint.getStrokeWidth();
        RectF boundary = new RectF();
        path.computeBounds(boundary, true);
        width = boundary.width() + paintStrokeWidth + PADDING;
        height = boundary.height() + paintStrokeWidth + PADDING;
        centerX = width / 2;
        centerY = height / 2;
        Position leftTop = new Position(boundary.centerX() - centerX, boundary.centerY() - centerY);
        path.offset(-leftTop.x, -leftTop.y);
        return leftTop;
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public void generate() {
        GenerationSlide<FreeSegment> gen = new GenerationSlide<>(FreeSegment.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<FreeSegment>() {
            @Override
            public void onActionDown(FreeSegment e, float x, float y, Canvas canvas) {
                e.getPath().moveTo(x, y);
            }

            @Override
            public void onActionMove(FreeSegment e, float x, float y, Canvas canvas, float startX, float startY, float prevX, float prevY) {
                Path path = e.getPath();
                path.lineTo(x, y);
                canvas.drawPath(path, e.getPaint());
            }

            @Override
            public void onActionUp(FreeSegment e, float x, float y, float startX, float startY) {

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
