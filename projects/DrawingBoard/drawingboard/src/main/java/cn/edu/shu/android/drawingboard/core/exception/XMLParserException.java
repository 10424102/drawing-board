package cn.edu.shu.android.drawingboard.core.exception;

import cn.edu.shu.android.drawingboard.xml.XMLParserBaseException;

/**
 * Created by yy on 1/22/14.
 */
public class XMLParserException extends XMLParserBaseException {
    public XMLParserException(){
        super();
    }
    public XMLParserException(String detailMessage) {
        super(detailMessage);
    }
    public XMLParserException(XMLParserBaseException e){
        super("[XML Base]"+e.toString());
    }
}
