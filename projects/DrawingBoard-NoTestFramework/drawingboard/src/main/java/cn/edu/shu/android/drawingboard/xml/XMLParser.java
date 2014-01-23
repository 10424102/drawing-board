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
        Block rootBlock = new Block();
        Attr attr = null;
        int attrcount = 0;
        int index = 0;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setValidating(false);
            factory.setNamespaceAware(false);
            parser = factory.newPullParser();
            // xmlPullParser的encoding参数为null则试图自动检测符合XML1.0标准的编码
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();
            block = new Block();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
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
                } else if (eventType == XmlPullParser.END_TAG) {
                    rootBlock.addSubBlock(block);
                    block = new Block();
                    attrcount = 0;
                }
                eventType = parser.next();
            }
            // 释放最后生成的Block
            block = null;
        } catch (XmlPullParserException e) {
            Log.e(XMLParser.class.getName(), "XMlParserError", e);
        } catch (IOException e) {
            Log.e(XMLParser.class.getName(), "XMlParserError", e);
        }
        return rootBlock;
    }

}
