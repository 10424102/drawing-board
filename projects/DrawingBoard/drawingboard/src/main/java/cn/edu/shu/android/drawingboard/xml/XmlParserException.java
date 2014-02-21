package cn.edu.shu.android.drawingboard.xml;

/**
 * Created by otakuplus on 14-1-24.
 * XMLParserBaseException是包装了XMLParser可能遇到的异常，这些异常通常是由IO操作和XML文档
 * 的编码格式，或者XML文档本身包含编写错误造成的。
 */
public class XmlParserException extends Exception {

    public XmlParserException() {
        super();
    }

    public XmlParserException(String message) {
        super(message);
    }

    public XmlParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmlParserException(Throwable cause) {
        super(cause);
    }
}
