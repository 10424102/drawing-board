package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Paint;

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
    private Paint paint;
    protected Position center = new Position();

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                       Constructor
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Element() {
        id = ++idCount;
        genTool = null;
        setPaint(null);
    }

    public Element(Element e) {
        id = ++idCount;
        genTool = e.getGenTool();
        setPaint(e.getPaint());
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

    public Position getCenter() {
        return new Position(center);
    }

    public void setCenter(Position center) {
        this.center = new Position(center);
    }

    public void setCenter(float x, float y) {
        center = new Position(x, y);
    }

    public void setPaint(Paint p) {
        if (p == null) {
            paint = new Paint();
        } else {
            paint = new Paint(p);
        }
//        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
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

    public abstract void measure(float startX, float startY, float endX, float endY);
}
