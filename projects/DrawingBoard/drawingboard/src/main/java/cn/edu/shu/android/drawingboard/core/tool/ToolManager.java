package cn.edu.shu.android.drawingboard.core.tool;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.exception.BuildToolException;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XMLParser;

/**
 * Created by yy on 1/22/14.
 */
public class ToolManager {
    private List<Tool> tools = new ArrayList<Tool>();
    private static ToolManager instance;
    private static MyApplication app = MyApplication.getInstance();

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }

    public void removeTool(int toolId){
        for(Tool t : tools){
            if(t.getId() == toolId){
                tools.remove(t);
                break;
            }
        }
    }

    private ToolManager() {

    }

    private void decompress(File f){

    }

    public void buildTool(String name) throws BuildToolException {
        String toolDir = app.APP_HOME + app.PLUGIN_DIR + name + "/";
        File toolXml = new File(toolDir + name + ".xml");
        if (toolXml.exists()) {
            buildToolByXML(toolDir + name + ".xml");
        } else throw new BuildToolException("Tool built file doesn't exists: " + toolXml.getPath());
    }

    public Tool buildToolByXML(String XMLFilePath) throws BuildToolException {
        Tool t = null;
        try{
            File xml = new File(XMLFilePath);
            FileInputStream fis = new FileInputStream(xml);
            Block root = new XMLParser().getRootBlock(fis);
            t = new Tool();
            t.loadXML(root);
            tools.add(t);
        }
        catch (Exception e){
            throw new BuildToolException(e);
        }
        return t;
    }

    public void loadAll() {
        //TODO load all
    }

    public List<ToolDisplayModel> getToolList()
    {
        List<ToolDisplayModel> ret = new ArrayList<>();
        for(Tool t : tools){
            ret.add(new ToolDisplayModel(t));
        }
        return ret;
    }

    public Tool getTool(int toolId) {
        for(Tool t : tools){
            if(t.getId() == toolId)return t;
        }
        return null;
    }

}
