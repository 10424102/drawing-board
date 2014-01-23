package cn.edu.shu.android.drawingboard.core.tool;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.exception.ParserXMLException;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XMLParser;

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

    public Tool buildToolByXML(String XMLFilePath) throws ParserXMLException, FileNotFoundException {
        MyApplication app = MyApplication.getInstance();
        Block root = XMLParser.getRootBlock(app.openFileInput(XMLFilePath));
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
