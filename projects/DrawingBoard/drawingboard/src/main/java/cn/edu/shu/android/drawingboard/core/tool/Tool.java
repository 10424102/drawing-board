package cn.edu.shu.android.drawingboard.core.tool;

import android.graphics.Paint;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Circle;
import cn.edu.shu.android.drawingboard.core.elements.Dot;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.FreeSegment;
import cn.edu.shu.android.drawingboard.core.elements.Rectangle;
import cn.edu.shu.android.drawingboard.core.elements.StaticPicture;
import cn.edu.shu.android.drawingboard.core.elements.StraightSegment;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

public class Tool implements XmlInitializable {
    private static int idCount = 0;
    private static final MyApplication app = MyApplication.getInstance();

    private Element content;
    private String iconPath;
    private String name;
    private int id;
    private String dirPath;

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public Element getContent() {
        return content;
    }

    public void setContent(Element mContent) {
        this.content = content;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String mIconPath) {
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Tool() {
        id = ++idCount;
    }

    public void startUsing() {
        app.setCurrentTool(this);
        content.generate(app.getPaintCanvas());
    }

    public Paint getDrawPaint() {
        return content.getDrawPaint();
    }

    public Paint getErasePaint() {
        return content.getErasePaint();
    }

    @Override
    public boolean xmlParse(Block block) throws XmlParserException {
        for (Iterator i = block.attrIterator(); i.hasNext(); ) {
            Attr a = (Attr) i.next();
            switch (a.getName().toLowerCase()) {
                case "name":
                    name = a.getValue();
                    break;
                case "icon-path":
                    iconPath = a.getValue();
                    break;
            }
        }
        for (Iterator i = block.blockIterator(); i.hasNext(); ) {
            Block b = (Block) i.next();
            switch (b.getName().toLowerCase()) {
                case "element":
                    Block bb = b.getFirstSubBlock();
                    switch (bb.getName().toLowerCase()) {
                        case "dot":
                            content = new Dot();
                            content.setGenTool(this);
                            ((Dot) content).xmlParse(bb);
                            break;
                        case "straight-segment":
                            content = new StraightSegment();
                            content.setGenTool(this);
                            ((StraightSegment) content).xmlParse(bb);
                            break;
                        case "free-segment":
                            content = new FreeSegment();
                            content.setGenTool(this);
                            ((FreeSegment) content).xmlParse(bb);
                            break;
                        case "circle":
                            content = new Circle();
                            content.setGenTool(this);
                            ((Circle) content).xmlParse(bb);
                            break;
                        case "rectangle":
                            content = new Rectangle();
                            content.setGenTool(this);
                            ((Rectangle) content).xmlParse(bb);
                            break;
                        case "static-picture":
                            content = new StaticPicture();
                            content.setGenTool(this);
                            ((StaticPicture) content).xmlParse(bb);
                            break;
                    }
                    break;
                case "element-group":
                    break;
            }
        }
        return true;
    }
}
