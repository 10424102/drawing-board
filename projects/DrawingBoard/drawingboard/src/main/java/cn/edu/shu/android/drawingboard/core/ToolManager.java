package cn.edu.shu.android.drawingboard.core;

import org.dom4j.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 1/22/14.
 */
public class ToolManager {
    private List<Tool> tools = new ArrayList<Tool>();
    private static ToolManager instance;

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }

    private ToolManager() {

    }

    public Tool buildToolByXML(Document doc) throws PhraseXMLException {
        org.dom4j.Element root = doc.getRootElement();
        Tool t = new Tool();
        t.loadXML(root);
        tools.add(t);
        return t;
    }

    public void loadAll() {
        //TODO load all
    }

    public Tool getTool(String name) {
        //TODO get tool by name
        return null;
    }

}
