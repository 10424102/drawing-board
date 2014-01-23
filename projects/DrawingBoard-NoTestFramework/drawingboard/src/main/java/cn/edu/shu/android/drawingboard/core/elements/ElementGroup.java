package cn.edu.shu.android.drawingboard.core.elements;

import android.view.View;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import cn.edu.shu.android.drawingboard.core.exception.ParserXMLException;
import cn.edu.shu.android.drawingboard.core.transform.Transform;
import cn.edu.shu.android.drawingboard.xml.Block;

/**
 * Created by yy on 1/22/14.
 */
public class ElementGroup extends Element {

    /**
     * 根据zindex从大到小排列的优先队列
     */
    private PriorityQueue<Element> group = new PriorityQueue<Element>(1, new Comparator<Element>() {
        @Override
        public int compare(Element lhs, Element rhs) {
            return lhs.getZindex() - rhs.getZindex();
        }
    });

    public ElementGroup()
    {
        setXMLBlockName("element-group");
    }

    public void addElement(Element e) {
        group.add(e);
    }

    public void removeElement(Element e) {
        group.remove(e);
    }

    //TODO modify element group generate

    /**
     * 组生成策略，也许不是，每个元素分别生成
     * @param v
     */
    @Override
    public void generate(View v) {
        for(Iterator<Element> i = group.iterator();i.hasNext();){
            Element e = i.next();
            e.generate(v);
        }
    }

    @Override
    public void loadXML(Block root) throws ParserXMLException {
        int zindex = 0;
        for(Iterator i = root.blockIterator();i.hasNext();zindex++){
            Block e = (Block)i.next();

            //TODO 这里一样的操作仅仅是因为工具名称不同
            if(e.getName().equalsIgnoreCase("point")){
                Element x = new Point();
                x.loadXML(e);
                x.setZindex(zindex);
                group.add(x);
            }else if(e.getName().equalsIgnoreCase("line")){
                //TODO generate different kind of element
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
