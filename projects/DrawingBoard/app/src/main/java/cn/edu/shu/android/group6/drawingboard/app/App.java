package cn.edu.shu.android.group6.drawingboard.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;

import cn.edu.shu.android.group6.drawingboard.app.core.tool.Tool;
import cn.edu.shu.android.group6.drawingboard.app.core.view.PaintCanvas;

/**
 * Created by yy on 2/25/14.
 */
public class App extends Application {

    public static final int MSG_CANVAS_ELEMENT_SELECTED = 1;
    public static final int MSG_CANVAS_ELEMENT_UNSELECTED = 2;
    public static final int MSG_CANVAS_ELEMENT_EDIT = 3;
    public static final int MSG_CANVAS_ELEMENT_DELETED = 4;

    private static App instance;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });
    private Activity mainActivity;
    private PaintCanvas paintCanvas;
    private Paint paint = new Paint();
    private Tool currentTool;

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public App() {
        super();
        instance = this;
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    public static App getInstance() {
        return instance;
    }

    public Paint getPaint() {
        return paint;
    }

    public PaintCanvas getPaintCanvas() {
        return paintCanvas;
    }

    public void setPaintCanvas(PaintCanvas paintCanvas) {
        this.paintCanvas = paintCanvas;
    }

    public Activity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Context getContext() {
        return mainActivity;
    }
}
