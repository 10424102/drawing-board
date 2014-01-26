package cn.edu.shu.android.drawingboard;

import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;
import cn.edu.shu.android.drawingboard.core.tool.ToolManager;

/**
 * Created by yy on 1/22/14.
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public void setPc(PaintCanvas pc) {
        this.pc = pc;
    }

    public PaintCanvas pc = null;
    public Bundle transfer;
    public static final String APP_HOME = Environment.getDataDirectory().getParent() + "/";
    public static final String PLUGIN_HOME = Environment.getDataDirectory().getParent() + "/plugins/";
    public static final String TEMPLATE_HOME = Environment.getDataDirectory().getParent() + "/templates/";
    public static final String GALLERY_HOME = Environment.getDataDirectory().getParent() + "/gallery/";
    private static int screenWidth;
    private static int screenHeight;

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    private void setScreenSize() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
        Log.i("yy", "Screen Size: " + screenWidth + "," + screenHeight);
    }

//    public MyApplication() {
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        transfer = new Bundle();
        setScreenSize();
        ToolManager mToolManager = ToolManager.getInstance();
        try {
            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");

            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");

            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");

            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");

            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");

            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");
            mToolManager.buildToolByXML("/DrawPointTool.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public Canvas getCanvas() {
        return pc.getCanvas();
    }

    public void update() {
        pc.update();
    }

}
