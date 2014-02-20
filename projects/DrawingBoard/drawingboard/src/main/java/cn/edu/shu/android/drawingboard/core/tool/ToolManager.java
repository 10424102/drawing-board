package cn.edu.shu.android.drawingboard.core.tool;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.exception.BuildToolException;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlParser;

/**
 * Created by yy on 1/22/14.
 */
public class ToolManager {
    private List<Tool> tools = new ArrayList<Tool>();
    private static ToolManager instance;
    private final static MyApplication app = MyApplication.getInstance();

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }

    public void removeTool(int toolId) {
        for (Tool t : tools) {
            if (t.getId() == toolId) {
                tools.remove(t);
                break;
            }
        }
    }

    public class ToolDisplayModel {
        private int id;
        private String name;
        private String iconPath;

        public ToolDisplayModel(Tool t) {
            id = t.getId();
            name = t.getName();
            iconPath = t.getIconPath();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getIconPath() {
            return iconPath;
        }
    }

    private ToolManager() {
    }

    public void buildTool(String name) throws BuildToolException {
        //String toolDir = app.APP_HOME + app.PLUGIN_DIR + name + "/";
        String toolDir = "/sdcard/drawingboard/" + app.PLUGIN_DIR + name + "/";
        File toolXml = new File(toolDir + name + ".xml");
        if (toolXml.exists()) {
            Tool t = new Tool();
            t.setDirPath(toolDir);
            buildToolByXML(t, toolXml.getPath());
            tools.add(t);
        } else throw new BuildToolException("Tool built file doesn't exists: " + toolXml.getPath());
    }

    public void buildToolByXML(Tool t, String XMLFilePath) throws BuildToolException {
        try {
            File xml = new File(XMLFilePath);
            if (!xml.exists()) throw new BuildToolException("Xml file not found.");
            FileInputStream fis = new FileInputStream(xml);
            Block root = new XmlParser().getRootBlock(fis);
            t.xmlParse(root);
        } catch (Exception e) {
            throw new BuildToolException(e);
        }
    }

    public List<ToolDisplayModel> getToolDisplayModelList() {
        List<ToolDisplayModel> ret = new ArrayList<>();
        for (Tool t : tools) {
            ret.add(new ToolDisplayModel(t));
        }
        return ret;
    }

    public Tool getTool(int toolId) {
        for (Tool t : tools) {
            if (t.getId() == toolId) return t;
        }
        return null;
    }

}
