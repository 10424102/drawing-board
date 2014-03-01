package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;

/**
 * Created by yy on 3/1/14.
 */
public class TranslateAnimationTool implements Generable {
    private static final App app = App.getInstance();

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
