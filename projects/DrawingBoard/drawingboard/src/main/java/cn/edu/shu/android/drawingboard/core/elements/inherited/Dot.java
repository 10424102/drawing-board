package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Canvas;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.GenerationClick;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlInitializer;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

public class Dot extends Element implements XmlInitializable {

    private float dotX;
    private float dotY;

    public float getDotX() {
        return dotX;
    }

    public float getDotY() {
        return dotY;
    }

    public void setDotXY(float x, float y) {
        dotX = x;
        dotY = y;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Dot() {
        super();
    }

    public Dot(Dot x) {
        super(x);
        dotX = x.getDotX();
        dotY = x.getDotY();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                        Other
    ////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public void measure(float startX, float startY, float endX, float endY) {
        center.set(startX, startY);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawPoint(dotX, dotY, getPaint());
    }

    @Override
    public void generate() {
        GenerationClick<Dot> gen = new GenerationClick<>(Dot.class);
        gen.setGenerationClickListener(new GenerationClick.GenerationClickListener<Dot>() {
            @Override
            public void onSetElement(Dot e, float clickX, float clickY) {
                e.setDotXY(clickX, clickY);
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
