package cn.edu.shu.android.drawingboard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.edu.shu.android.drawingboard.core.tool.Tool;
import cn.edu.shu.android.drawingboard.core.tool.ToolManager;
import cn.edu.shu.android.drawingboard.view.PaintCanvas;

/**
 * Created by yy on 1/22/14.
 */
public class MyApplication extends Application {
    public static enum AppStatus {
        CLEAR,
        ERROR
    }

    public class ErrorMessage {
        public String content;

        public ErrorMessage(String err) {
            content = err;
        }
    }

    private static MyApplication instance;
    public static final String APP_HOME;
    public static final String PLUGIN_DIR;
    public static final String TEMPLATE_DIR;
    public static final String GALLERY_DIR;
    public static final String[] defaultToolList = new String[]{
            "DrawPointTool",
            "DrawStraightSegmentTool",
            "DrawFreeSegmentTool",
            "DrawCircleTool",
            "DrawRectangleTool",
            "DrawStaticPictureTool"
    };
    private static AppStatus status;

    public static int screenWidth;
    public static int screenHeight;
    private List<PaintCanvas> pclist = new ArrayList<>();
    private PaintCanvas currentpc;
    private Activity mainActivity;
    private Errorbox errorBox;
    private List<ErrorMessage> errorMessageList = new LinkedList<>();
    public Bundle data;
    private int canvasWidth;
    private int canvasHeight;
    private Paint paint;
    private Tool currentTool;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    static {
        APP_HOME = Environment.getExternalStorageDirectory().getPath() + "/drawingboard/";
        PLUGIN_DIR = APP_HOME + "plugins/";
        TEMPLATE_DIR = APP_HOME + "templates/";
        GALLERY_DIR = APP_HOME + "gallery/";
        createDir(APP_HOME);
        createDir(PLUGIN_DIR);
        createDir(TEMPLATE_DIR);
        createDir(GALLERY_DIR);
    }

    private static void createDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdir();
    }

    public static MyApplication getInstance() {
        return instance;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Getter & Setter
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setMainActivity(Activity a) {
        mainActivity = a;
    }

    public Activity getMainActivity() {
        return mainActivity;
    }

    public PaintCanvas getPaintCanvas() {
        return currentpc;
    }

    public AppStatus getStatus() {
        return status;
    }

    public void setErrorBox(Errorbox fragment) {
        errorBox = fragment;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setCurrentTool(Tool tool) {
        currentTool = tool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public Context getContext() {
        return mainActivity;
    }

    public Paint getPaint() {
        return paint;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                         Other
    ////////////////////////////////////////////////////////////////////////////////////////////////

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
            for (String name : defaultToolList) {
                mToolManager.buildTool(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void error(String msg) {
        ErrorMessage err = new ErrorMessage(msg);
        errorMessageList.add(err);
    }

    public void showErrorAndExit(String msg) {
        ErrorMessage err = new ErrorMessage(msg);
        errorMessageList.add(err);
    }

    public void showError() {
        errorBox.setContent(errorMessageList.toString());
        mainActivity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .show(errorBox)
                .commit();
    }

    public void hideError() {
        errorBox.setContent(errorMessageList.toString());
        mainActivity.getFragmentManager().beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .hide(errorBox)
                .commit();
    }

    public void registerPaintCanvas(PaintCanvas pc) {
        pclist.add(pc);
    }

    public void setCurrentPaintCanvas(PaintCanvas pc) {
        currentpc = pc;
    }

    public void setPaintCanvasOnTouchListener(View.OnTouchListener l) {
        for (PaintCanvas pc : pclist) {
            pc.setOnTouchListener(l);
        }
    }
}
