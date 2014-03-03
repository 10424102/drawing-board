package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;
import cn.edu.shu.android.group6.drawingboard.app.core.tool.Tool;

/**
 * Created by yy on 2/26/14.
 */
public abstract class Gaia implements Generable {
    protected static final App app = App.getInstance();
    protected Paint paint;
    protected Paint erasePaint;
    protected float width;
    protected float height;
    protected float top;
    protected float left;
    protected float cx;
    protected float cy;
    protected Tool genTool;

    public Tool getGenTool() {
        return genTool;
    }

    public void setGenTool(Tool genTool) {
        this.genTool = genTool;
    }

    public Gaia(Tool genTool) {
        this.genTool = genTool;
    }

    public void setLeftTop(float l, float t) {
        left = l;
        top = t;
    }

    public void setSize(float w, float h) {
        width = w;
        cx = width / 2;
        height = h;
        cy = height / 2;
    }

    public float getTop() {
        return top;
    }

    public float getLeft() {
        return left;
    }

    protected void setPaint(Paint p) {
        paint = new Paint(p);
        erasePaint = new Paint(p);
        erasePaint.setStrokeWidth(erasePaint.getStrokeWidth() + 2);
        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public abstract void paint(Canvas canvas);

    public abstract void generate();

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Gaia(Gaia g) {
        this.genTool = g.genTool;
        this.cx = g.cx;
        this.cy = g.cy;
        this.erasePaint = g.erasePaint;
        this.paint = g.paint;
        this.width = g.width;
        this.height = g.height;
        this.left = g.left;
        this.top = g.top;
    }
}
