package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
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
    public void measure(float startX, float startY, float endX, float endY) {
        pureWidth = 0;
        pureHeight = 0;
        calculateRealSize();
        center.set(startX, startY);
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        super.paint(canvas, paint);
        canvas.drawPoint(width / 2, height / 2, drawPaint);
    }

    @Override
    public void generate(View v) {
        GenerationClick<Dot> gen = new GenerationClick<>(Dot.class);
        gen.setGenerationClickListener(new GenerationClick.GenerationClickListener<Dot>() {
            @Override
            public void onSetElement(Dot e, float clickX, float clickY) {
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
