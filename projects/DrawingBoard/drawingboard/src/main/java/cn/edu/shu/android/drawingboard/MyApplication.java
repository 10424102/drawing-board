package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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
    private PaintCanvas mPaintCanvas = null;
    private Paint mPaint = null;
    private Activity mMainActivity;

    public void setMainActivity(Activity a) {
        mMainActivity = a;
    }

    public Activity getMainActivity() {
        return mMainActivity;
    }

    public void setPaintCanvas(PaintCanvas pc) {
        mPaintCanvas = pc;
    }

    public PaintCanvas getPaintCanvas() {
        return mPaintCanvas;
    }

    public Paint getPaint() {
        return mPaint;
    }

    public Bundle transfer;
    public static final String APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/drawingboard/";
    public static final String PLUGIN_DIR = "plugins/";
    public static final String TEMPLATE_DIR = "templates/";
    public static final String GALLERY_DIR = "gallery/";
    private static int screenWidth;
    private static int screenHeight;

    static {
        File home = new File(APP_HOME);
        if (!home.exists()) {
            if (home.mkdir()) {
                Log.i("yy", "Created home directory.");
            }
        } else {
            Log.i("yy", "Home directory exists.");
        }

        File plugins = new File(APP_HOME + PLUGIN_DIR);
        if (!plugins.exists()) {
            if (plugins.mkdir()) {
                Log.i("yy", "Created plugin directory.");
            }
        } else {
            Log.i("yy", "Plugin directory exists.");
        }

        File templates = new File(APP_HOME + TEMPLATE_DIR);
        if (!templates.exists()) {
            if (templates.mkdir()) {
                Log.i("yy", "Created template directory.");
            }
        } else {
            Log.i("yy", "Template directory exists.");
        }

        File gallery = new File(APP_HOME + GALLERY_DIR);
        if (!gallery.exists()) {
            if (gallery.mkdir()) {
                Log.i("yy", "Created gallery directory.");
            }
        } else {
            Log.i("yy", "Gallery directory exists.");
        }
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    private void setScreenSize() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        transfer = new Bundle();

        //get screen size
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //initialize paint
        mPaint = new Paint();

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


    private Tool mCurrentTool;

    public void setCurrentTool(Tool tool) {
        mCurrentTool = tool;
    }

    public Tool getCurrentTool() {
        return mCurrentTool;
    }

    public Context getContext() {
        return mPaintCanvas.getContext();
    }

}
