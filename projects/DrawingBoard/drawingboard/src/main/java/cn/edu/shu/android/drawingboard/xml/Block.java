package cn.edu.shu.android.drawingboard.xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.Iterator;
import java.util.List;

/**
 * Created by yy on 1/23/14.
 */
public class Block {
    private XmlPullParser xpp;
    public Iterator<Block> blockIterator(){
        return null;
    }
    public Iterator<Attr> attrIterator(){
        return null;
    }
    public String getName(){
        return null;
    }
    public int subBlockCount(){
        return 0;
    }
    public Block getFirstSubBlock(){
        return null;
    }
    public List<Block> getSubBlocks(){
        return null;
    }
    public String getAttrValue(String attrName){
        return null;
    }
    public boolean hasSubBlock(){
        return false;
    }
    public List<Block> getSubBlocksByName(String subBlockName){
        return null;
    }
}
