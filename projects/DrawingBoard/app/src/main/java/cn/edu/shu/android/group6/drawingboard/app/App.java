package cn.edu.shu.android.group6.drawingboard.app;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;

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
    //private PaintCanvas paintCanvas;
    private MainActivity mainActivity;

    public App() {
        super();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

//    public PaintCanvas getPaintCanvas() {
//        return paintCanvas;
//    }
//
//    public void setPaintCanvas(PaintCanvas paintCanvas) {
//        this.paintCanvas = paintCanvas;
//    }

    public MainActivity getMainActivity() {
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
}
