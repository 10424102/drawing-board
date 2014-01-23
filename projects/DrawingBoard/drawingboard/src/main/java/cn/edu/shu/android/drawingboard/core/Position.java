package cn.edu.shu.android.drawingboard.core;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.exception.ParserXMLException;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;

/**
 * Created by yy on 1/22/14.
 */
public class Position {
    private float x;
    private float y;

    public void setX(float x) {
        this.x = x;
    }

    public void loadXML(Block root) throws ParserXMLException {
        if(!root.getName().toLowerCase().equals("center")) {
            throw new ParserXMLException("Try to use a non-center xml to initialize the Position object.");
        }
        for(Iterator<Attr> i = root.attrIterator();i.hasNext();){
            Attr a = i.next();
            if(a.getName().toLowerCase().equals("x")){
                try{
                    x = Integer.parseInt(a.getValue());
                }
                catch (NumberFormatException e){
                    throw new ParserXMLException("Value syntax error: center -> x.");
                }
            }
            else if(a.getName().toLowerCase().equals("y")){
                try{
                    x = Integer.parseInt(a.getValue());
                }
                catch (NumberFormatException e){
                    throw new ParserXMLException("Value syntax error: center -> y.");
                }
            }
            else
            {
                throw new ParserXMLException("center only support attributes: x and y.");
            }
        }
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {

        return x;
    }

    public float getY() {
        return y;
    }
}
