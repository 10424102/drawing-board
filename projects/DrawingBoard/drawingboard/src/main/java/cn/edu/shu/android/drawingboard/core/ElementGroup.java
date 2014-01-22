package cn.edu.shu.android.drawingboard.core;

import android.view.View;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by yy on 1/22/14.
 */
public class ElementGroup extends Element {
    private PriorityQueue<Element> group = new PriorityQueue<Element>(1, new Comparator<Element>() {
        @Override
        public int compare(Element lhs, Element rhs) {
            return lhs.getZindex() - rhs.getZindex();
        }
    });

    public void addElement(Element e) {
        group.add(e);
    }

    public void removeElement(Element e) {
        group.remove(e);
    }

    @Override
    public void generate(View v) {

    }

    @Override
    public void loadXML(org.dom4j.Element root) throws PhraseXMLException {
        int zindex = 0;
        for(Iterator i = root.elementIterator();i.hasNext();zindex++){
            org.dom4j.Element e = (org.dom4j.Element)i.next();
            if(e.getName().equalsIgnoreCase("point")){
                Element x = new Point();
                x.loadXML(e);
                x.setZindex(zindex);
                group.add(x);
            }else if(e.getName().equalsIgnoreCase("line")){

            }
        }
    }

    @Override
    public void draw() {
        for (Element e : group) {
            e.draw();
        }
    }

    @Override
    public void transform(Transform t) {

    }
}
