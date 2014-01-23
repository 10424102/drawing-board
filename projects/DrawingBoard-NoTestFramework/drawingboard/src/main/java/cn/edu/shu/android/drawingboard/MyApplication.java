package cn.edu.shu.android.drawingboard;

import android.app.Application;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;

/**
 * Created by yy on 1/22/14.
 */
public class MyApplication extends Application {
    private static MyApplication instance = null;
    public  PaintCanvas pc = null;

    private MyApplication(){

    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance(){
        return instance;
    }
}
