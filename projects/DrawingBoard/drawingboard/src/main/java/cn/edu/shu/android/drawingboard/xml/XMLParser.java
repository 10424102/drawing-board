package cn.edu.shu.android.drawingboard.xml;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yy on 1/23/14.
 * XMLParser用于提供XML文件的解析
 */
public class XMLParser {

    /**
     * 解析XML文件并将文件内容填充到Block中
     *
     * @param inputStream 输入流
     * @return Block 返回位于XML文件根部的一个Block
     */
    public static Block getRootBlock(InputStream inputStream) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        Block block = null;
        Block parent = null;
        Block rootBlock = new Block();
        rootBlock.setName("Root0");
        Attr attr = null;
        int attrcount = 0;
        int index = 0;
        int depth = 0;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(false);
            parser = factory.newPullParser();
            //xmlPullParser的encoding参数为null则试图自动检测符合XML1.0标准的编码
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();
            parent = rootBlock;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    // getDepth方法返回的层数以最外层为0层，第一个根节点为1层计算
                    if(depth != 0 && depth != parser.getDepth()){
                        parent = parent.getLastSubBlock();
                    }
                    block = new Block();
                    block.setName(parser.getName());
                    attrcount = parser.getAttributeCount();
                    if (attrcount != -1) {
                        for (index = 0; index < attrcount; index++) {
                            attr = new Attr();
                            attr.setName(parser.getAttributeName(index));
                            attr.setValue(parser.getAttributeValue(index));
                            block.addAttr(attr);
                        }
                    }
                    // 第2层结构开始，添加父子关系
                    block.setParentBlock(parent);
                    parent.addSubBlock(block);
                    depth = parser.getDepth();
                } else if (eventType == XmlPullParser.END_TAG) {
                    //每遇到一个闭标签，说明这一层一个标签结束，应该更新parent
                    if (block.getParentBlock() != null) {
                        parent = block.getParentBlock();
                    }
                    depth--;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(XMLParser.class.getName(), "XMlParserError", e);
        } catch (IOException e) {
            Log.e(XMLParser.class.getName(), "XMlParserError", e);
        }
        return rootBlock;
    }
}
