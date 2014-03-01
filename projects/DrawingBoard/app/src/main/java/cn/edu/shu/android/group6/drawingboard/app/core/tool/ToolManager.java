package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaCircle;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaCurve;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaLine;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaRectangle;
import cn.edu.shu.android.group6.drawingboard.app.core.element.GaiaRoundRectangle;

/**
 * Created by yy on 2/27/14.
 */
public class ToolManager {
    private static final App app = App.getInstance();
    private static final List<Tool> tools = new ArrayList<>();

    public static Tool load(String name) throws LoadToolException {
        try {
            InputStream is = app.getAssets().open("tools/" + name);
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Element root = document.getRootElement();
            if (!root.getName().equals("tool"))
                throw new LoadToolException("Root element is not 'tool'.");

            Tool tool = new Tool();
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
            Generable generator = null;
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element e = (Element) i.next();
                if (e.getName().equals("generator")) {
                    Element gen = (Element) e.elements().get(0);
                    switch (gen.getName()) {
                        case "circle":
                            generator = new GaiaCircle();
                            break;
                        case "curve":
                            generator = new GaiaCurve();
                            break;
                        case "line":
                            generator = new GaiaLine();
                            break;
                        case "rectangle":
                            generator = new GaiaRectangle();
                            break;
                        case "round-rectangle":
                            generator = new GaiaRoundRectangle();
                            break;
                        case "select":
                            generator = new SelectTool();
                            break;
                        case "delete":
                            generator = new DeleteTool();
                            break;
                        case "translate-animation":
                            generator = new TranslateAnimationTool();
                            break;
                    }
                }
            }
            tool.setGenerator(generator);
            tools.add(tool);
            return tool;
        } catch (Exception e) {
            throw new LoadToolException(e);
        }
    }

    public static List<Tool> getTools() {
        return tools;
    }
}
