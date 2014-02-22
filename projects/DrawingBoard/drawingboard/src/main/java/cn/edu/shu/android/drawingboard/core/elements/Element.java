package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Paint;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.Generable;
import cn.edu.shu.android.drawingboard.core.Paintable;
import cn.edu.shu.android.drawingboard.core.tool.Tool;

public abstract class Element implements Generable, Paintable {
    public static final float PADDING = 20;
    private static int idCount = 0;
    protected static MyApplication app = MyApplication.getInstance();

    protected int id;
    protected Tool genTool;
    protected Paint paint;
    protected float width;
    protected float height;
    protected float centerX;
    protected float centerY;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                       Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Element() {
        id = ++idCount;
        genTool = null;
        setPaint(null);//create a new paint
    }

    public Element(Element e) {
        id = ++idCount;
        genTool = e.getGenTool();
        setPaint(e.getPaint());//copy e's paint
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

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint p) {
        if (p == null) {
            paint = new Paint();
        } else {
            paint = new Paint(p);
        }
        paint.setAntiAlias(true);
//        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
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

    public abstract Position measure(float startX, float startY, float endX, float endY);

    public abstract boolean inside(float x, float y);
}
