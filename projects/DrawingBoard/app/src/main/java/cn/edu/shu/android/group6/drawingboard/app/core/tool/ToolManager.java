package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;
import cn.edu.shu.android.group6.drawingboard.app.core.XmlInitializable;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaCircle;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaCurve;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaLine;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaRectangle;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaRoundRectangle;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaStaticPicture;

/**
 * Created by yy on 2/27/14.
 */
public class ToolManager {
    private static final App app = App.getInstance();
    private static final List<Tool> tools = new ArrayList<>();

    private static Tool loadFromAssets(String url) {

        return null;
    }

    private static Tool loadFromSdcard(String url) {
        return null;
    }

    /**
     * give the tool root directory
     * like Assets://tools/CocaCola/CokeZero
     *
     * @param uri
     * @return
     */
    public static Tool load(URI uri) {
        Tool t = null;
        try {
            URI toolUri = new URI(uri.toString() + "/tool.xml");
            SAXReader reader = new SAXReader();
            Document document = reader.read(app.getAssets().open(toolUri.getHost() + toolUri.getPath()));
            Element root = document.getRootElement();
            if (!root.getName().equals("tool"))
                throw new LoadToolException("Root element is not 'tool'.");
            Tool tool = new Tool();
            tool.setDirPath(uri.toString());

            //attributes
            for (Iterator i = root.attributeIterator(); i.hasNext(); ) {
                Attribute a = (Attribute) i.next();
                switch (a.getName()) {
                    case "name":
                        tool.setName(a.getValue());
                        break;
                    case "oneoff":
                        tool.setOneoff(Boolean.parseBoolean(a.getValue()));
                        break;
                }
            }

            //elements
            Generable generator = null;
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element e = (Element) i.next();
                if (e.getName().equals("generator")) {
                    Element gen = (Element) e.elements().get(0);
                    switch (gen.getName()) {
                        case "circle":
                            generator = new GaiaCircle(tool);
                            break;
                        case "curve":
                            generator = new GaiaCurve(tool);
                            break;
                        case "line":
                            generator = new GaiaLine(tool);
                            break;
                        case "rectangle":
                            generator = new GaiaRectangle(tool);
                            break;
                        case "round-rectangle":
                            generator = new GaiaRoundRectangle(tool);
                            break;
                        case "select":
                            generator = new SelectTool();
                            break;
                        case "play-animation":
                            generator = new PlayAnimationTool();
                            break;
                        case "delete":
                            generator = new DeleteTool();
                            break;
                        case "translate-animation":
                            generator = new TranslateAnimationTool();
                            break;
                        case "static-picture":
                            generator = new GaiaStaticPicture(tool);
                            ((XmlInitializable) generator).parseXmlElement(gen);
                            break;
                    }
                }
            }
            tool.setGenerator(generator);
            tools.add(tool);
            return tool;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static List<Tool> getTools() {
        return tools;
    }
}
