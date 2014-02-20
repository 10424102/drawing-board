package cn.edu.shu.android.drawingboard.core;

import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

/**
 * Created by yy on 2/20/14.
 */
public interface XmlInitializable {
    public boolean xmlParse(Block block) throws XmlParserException;
}
