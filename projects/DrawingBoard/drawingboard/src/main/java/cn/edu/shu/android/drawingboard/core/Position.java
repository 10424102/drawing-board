package cn.edu.shu.android.drawingboard.core;

import org.dom4j.Attribute;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.exception.ParserXMLException;

/**
 * Created by yy on 1/22/14.
 */
public class Position {
    private float x;
    private float y;

    public void setX(float x) {
        this.x = x;
    }

    public void loadXML(org.dom4j.Element root) throws ParserXMLException {
        if(!root.getName().toLowerCase().equals("center")) {
            throw new ParserXMLException("Try to use a non-center xml to initialize the Position object.");
        }
        for(Iterator i = root.attributeIterator();i.hasNext();){
            Attribute a = (Attribute)i.next();
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
