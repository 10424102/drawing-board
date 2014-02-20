package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
    public void measure(float startX, float startY, float endX, float endY) {
        //In this method mDrawPaint has been setted
        RectF boundary = new RectF();
        path.computeBounds(boundary, true);
        float bw = boundary.width();
        float bh = boundary.height();
        pureWidth = bw;
        pureHeight = bh;
        calculateRealSize();
        //adjust coordinate system
        float paddingLeft = (width - bw) / 2;
        float paddingTop = (height - bh) / 2;
        path.offset(-boundary.left + paddingLeft, -boundary.top + paddingTop);
        center.set(boundary.centerX(), boundary.centerY());
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        super.paint(canvas, paint);
        canvas.drawPath(path, drawPaint);
    }

    @Override
    public void generate(View v) {
        GenerationSlide<FreeSegment> gen = new GenerationSlide<>(FreeSegment.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<FreeSegment>() {
            @Override
            public void onActionDown(FreeSegment e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint) {
                e.getPath().moveTo(x, y);
            }

            @Override
            public void onActionMove(FreeSegment e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                Path path = e.getPath();
                path.lineTo(x, y);
                pc.getCanvas().drawPath(path, drawPaint);
                pc.invalidate();
            }

            @Override
            public void onActionUp(FreeSegment e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawPath(e.getPath(), erasePaint);
                pc.invalidate();
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
