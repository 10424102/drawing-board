package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;

/**
 * Created by yy on 2/27/14.
 */
public class Tool {
    protected static final App app = App.getInstance();
    private Generable generator;
    private String name = "No name";
    private View view;
    private boolean oneoff = false;

    public boolean isOneoff() {
        return oneoff;
    }

    public void setOneoff(boolean oneoff) {
        this.oneoff = oneoff;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setGenerator(Generable generator) {
        this.generator = generator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tool() {

    }

    public void startUsing() {
        if (generator != null) generator.generate();
        if (!oneoff) {
            ((Button) app.getCurrentTool().getView()).setTextColor(Color.BLACK);
            app.setCurrentTool(this);
        }
    }
}
