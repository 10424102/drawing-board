package cn.edu.shu.android.drawingboard.core.elements;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.exception.BuildElementException;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;

/**
 * Created by yy on 1/22/14.
 */


public class Position {
    private float x;
    private float y;

    public Position(Block b) throws BuildElementException{
        loadXML(b);
    }


    public void setX(float x) {
        this.x = x;
    }

    public void setX(String s) throws BuildElementException {
        try {
            x = Float.parseFloat(s);
        } catch (Exception e) {
            throw new BuildElementException(e);
        }
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setY(String s) throws BuildElementException {
        try {
            y = Float.parseFloat(s);
        } catch (Exception e) {
            throw new BuildElementException(e);
        }
    }

    public void loadXML(Block root) throws BuildElementException {
        if (root.getName().equalsIgnoreCase("center")) {
            for (Iterator<Attr> i = root.attrIterator(); i.hasNext(); ) {
                Attr a = i.next();
                switch (a.getName().toLowerCase()) {
                    case "x":
                        setX(a.getValue());
                        break;
                    case "y":
                        setY(a.getValue());
                        break;
                }
            }
        }
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
