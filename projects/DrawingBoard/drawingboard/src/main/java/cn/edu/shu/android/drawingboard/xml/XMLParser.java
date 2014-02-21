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
public class XmlParser {

    private Block root;
    private Block parent;
    private Block current;
    private int depth;

    /**
     * 解析XML文件并将文件内容填充到Block中
     *
     * @param inputStream 输入流
     * @return Block 返回位于XML文件根部的一个Block
     */
    public Block getRootBlock(InputStream inputStream) throws XmlParserException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            //xmlPullParser的encoding参数为null则试图自动检测符合XML1.0标准的编码
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (parser.getDepth() > depth) {
                            depth = parser.getDepth();
                            parent = current;
                        }
                        current = new Block(parser.getName(), parent);
                        if (root == null) {
                            root = current;
                        }
                        if (parent != null) {
                            parent.getSubBlocks().add(current);
                        }
                        int attrCount = parser.getAttributeCount();
                        for (int i = 0; i < attrCount; i++) {
                            current.addAttr(new Attr(parser.getAttributeName(i), parser.getAttributeValue(i)));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getDepth() < depth) {
                            depth = parser.getDepth();
                            current = current.getParentBlock();
                            parent = current.getParentBlock();
                        }
                        if (depth == 1) {
                            return root;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e(XmlParser.class.getName(), "XMlParserError", e);
            throw new XmlParserException("Error caused by XMLPullParser while parsing xml!");
        } catch (IOException e) {
            Log.e(XmlParser.class.getName(), "XMlParserError", e);
            throw new XmlParserException("Error caused by io reading while parsing xml!");
        }
        return null;
    }
}
