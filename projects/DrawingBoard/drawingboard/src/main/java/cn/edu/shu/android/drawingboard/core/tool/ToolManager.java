package cn.edu.shu.android.drawingboard.core.tool;

import android.os.Environment;

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

    private ToolManager() {

    }

    private void decompress(File f){

    }

    public void loadTool(String name) throws BuildToolException{

    }

    public void loadToolFromDir(String path) throws BuildToolException{

    }

    public void loadToolFromFile(String path) throws BuildToolException{
        try{
            File src = new File(path);
            //TODO copy to /tmp
            //TODO decompress it
            //TODO load
            //TODO clean
        }
        catch (Exception e)
        {
            throw new BuildToolException(e);
        }
    }

    public Tool buildToolByXML(String XMLFilePath) throws BuildToolException {
        Tool t = null;
        try{
            File xml = new File(Environment.getExternalStorageDirectory().getPath() + XMLFilePath);
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

    public List<Tool> getToolList()
    {
        return tools;
    }

    public Tool getToolById(int id) {
        for(Tool t : tools){
            if(t.getId() == id)return t;
        }
        return null;
    }

}
