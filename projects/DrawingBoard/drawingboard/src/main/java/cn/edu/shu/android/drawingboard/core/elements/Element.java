package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.Generable;
import cn.edu.shu.android.drawingboard.core.Paintable;
import cn.edu.shu.android.drawingboard.core.tool.Tool;

public abstract class Element implements Generable, Paintable {
    public static final float PADDING = 10;
    private static int idCount = 0;
    protected static MyApplication app = MyApplication.getInstance();

    protected int id;
    protected Tool genTool;
    protected float width;
    protected float height;
    protected float pureWidth;
    protected float pureHeight;
    protected Paint drawPaint;
    protected Paint erasePaint;
    protected Position center = new Position();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //                                       Constructor
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Element() {
        id = ++idCount;
        genTool = null;
        pureWidth = 0;
        pureHeight = 0;
        setPaint(null);
        calculateRealSize();
    }

    public Element(Element e) {
        id = ++idCount;
        genTool = e.getGenTool();
        pureWidth = e.getPureWidth();
        pureHeight = e.getPureHeight();
        setPaint(e.getDrawPaint());
        calculateRealSize();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Getter & Setter
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public int getId() {
        return id;
    }

    public Tool getGenTool() {
        return genTool;
    }

    public void setGenTool(Tool genTool) {
        this.genTool = genTool;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getPureWidth() {
        return pureWidth;
    }

    public void setPureWidth(float pureWidth) {
        this.pureWidth = pureWidth;
    }

    public float getPureHeight() {
        return pureHeight;
    }

    public void setPureHeight(float pureHeight) {
        this.pureHeight = pureHeight;
    }

    public Paint getDrawPaint() {
        return drawPaint;
    }

    public void setDrawPaint(Paint drawPaint) {
        this.drawPaint = drawPaint;
    }

    public Paint getErasePaint() {
        return erasePaint;
    }

    public void setErasePaint(Paint erasePaint) {
        this.erasePaint = erasePaint;
    }

    public Position getCenter() {
        return center;
    }

    public void setCenter(Position center) {
        this.center = center;
    }

    public int getWidthInt() {
        return (int) width;
    }

    public int getHeightInt() {
        return (int) height;
    }

    public void setPaint(Paint p) {
        if (p == null) {
            drawPaint = new Paint();
            erasePaint = new Paint();
        } else {
            drawPaint = new Paint(p);
            erasePaint = new Paint(p);
        }
        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                    Other
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (o instanceof Element) {
            if (id == ((Element) o).getId()) return true;
        }
        return false;
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        if (paint != null) setPaint(paint);
    }

    protected void calculateRealSize() {
        float strokeWidth = drawPaint.getStrokeWidth();
        width = pureWidth + strokeWidth + 2 * PADDING;
        height = pureHeight + strokeWidth + 2 * PADDING;
    }

    public abstract void measure(float startX, float startY, float endX, float endY);
}
