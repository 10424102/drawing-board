package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;

/**
 * Created by yy on 3/1/14.
 */
public class PlayAnimationTool implements Generable {
    @Override
    public void generate() {
        App.getInstance().getPaintCanvas().getAnimatorSet().start();
    }
}
