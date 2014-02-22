package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.GenerationClick;
import cn.edu.shu.android.drawingboard.core.elements.Position;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlInitializer;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

public class Dot extends Element implements XmlInitializable {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Dot() {
        super();
    }

    public Dot(Dot x) {
        super(x);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        Other
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public Position measure(float startX, float startY, float endX, float endY) {
        float paintStrokeWidth = paint.getStrokeWidth();
        width = paintStrokeWidth + PADDING;
        height = paintStrokeWidth + PADDING;
        centerX = width / 2;
        centerY = height / 2;
        return new Position(startX - centerX, startY - centerY);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawPoint(width / 2, height / 2, paint);
    }

    @Override
    public void generate() {
        GenerationClick<Dot> gen = new GenerationClick<>(Dot.class);
        gen.setGenerationClickListener(new GenerationClick.GenerationClickListener<Dot>() {
            @Override
            public void onSetElement(Dot e, float clickX, float clickY) {
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
        double dx = x - centerX;
        double dy = y - centerY;
        if (Math.sqrt(dx * dx + dy * dy) <= 24) return true;
        return false;
    }
}
