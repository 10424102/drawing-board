package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;
import cn.edu.shu.android.drawingboard.core.tool.Tool;
import cn.edu.shu.android.drawingboard.core.tool.ToolManager;

/**
 * Created by yy on 1/22/14.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static final String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/drawingboard/";
    public static final String PLUGIN_DIR = "plugins/";
    public static final String TEMPLATE_DIR = "templates/";
    public static final String GALLERY_DIR = "gallery/";
    public static int screenWidth;
    public static int screenHeight;
    private PaintCanvas pc;
    public Activity mainActivity;
    public Bundle data;
    public int canvasWidth;
    public int canvasHeight;
    public Paint paint;
    private Tool currentTool;

    public void setMainActivity(Activity a) {
        mainActivity = a;
    }

    public Activity getMainActivity() {
        return mainActivity;
    }

    public void setPaintCanvas(PaintCanvas pc) {
        this.pc = pc;
    }

    public PaintCanvas getPaintCanvas() {
        return pc;
    }

    static {
        File home = new File(APP_HOME);
        if (!home.exists()) {
            if (home.mkdir()) {
            }
        } else {
        }

        File plugins = new File(APP_HOME + PLUGIN_DIR);
        if (!plugins.exists()) {
            if (plugins.mkdir()) {
            }
        } else {
        }

        File templates = new File(APP_HOME + TEMPLATE_DIR);
        if (!templates.exists()) {
            if (templates.mkdir()) {
            }
        } else {
        }

        File gallery = new File(APP_HOME + GALLERY_DIR);
        if (!gallery.exists()) {
            if (gallery.mkdir()) {
            }
        } else {
        }
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        data = new Bundle();

        //get screen size
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //initialize paint
        paint = new Paint();

        //load 'plugins/'
        ToolManager mToolManager = ToolManager.getInstance();
        try {
            mToolManager.buildTool("DrawPointTool");
            mToolManager.buildTool("DrawStraightSegmentTool");
            mToolManager.buildTool("DrawFreeSegmentTool");
            mToolManager.buildTool("DrawCircleTool");
            mToolManager.buildTool("DrawRectangleTool");
            mToolManager.buildTool("DrawStaticPictureTool");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MyApplication getInstance() {
        return instance;
    }


    public void setCurrentTool(Tool tool) {
        currentTool = tool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public Context getContext() {
        return pc.getContext();
    }

}
