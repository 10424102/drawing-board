package cn.edu.shu.android.drawingboard.core.elements;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

/**
 * Created by yy on 1/22/14.
 */


public class Position implements XmlInitializable {
    public float x;
    public float y;

    public Position() {
        x = 0;
        y = 0;
    }

    public Position(Position p) {
        if (p != null) {
            x = p.x;
            y = p.y;
        }
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void offset(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean xmlParse(Block block) throws XmlParserException {
        for (Iterator i = block.attrIterator(); i.hasNext(); ) {
            Attr a = (Attr) i.next();
            switch (a.getName().toLowerCase()) {
                case "x":
                    x = Float.parseFloat(a.getValue());
                    break;
                case "y":
                    y = Float.parseFloat(a.getValue());
                    break;
            }
        }
        return true;
    }
}
