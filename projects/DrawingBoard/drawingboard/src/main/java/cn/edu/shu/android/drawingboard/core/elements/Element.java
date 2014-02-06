package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.Generable;
import cn.edu.shu.android.drawingboard.core.Paintable;
import cn.edu.shu.android.drawingboard.core.tool.Tool;

public abstract class Element implements Generable, Paintable {
    public static final float PADDING = 10;

    private static int mIdCount = 0;
    protected static MyApplication app = MyApplication.getInstance();

    private int mId;

    public int getId() {
        return mId;
    }

    private Tool mGenTool;

    public Tool getGenTool() {
        return mGenTool;
    }

    public void setGenTool(Tool tool) {
        mGenTool = tool;
    }

    private float mWidth = 0;

    public float getWidth() {
        return mWidth;
    }

    protected void setWidth(float width) {
        if (width > 0) mWidth = width;
    }

    private float mHeight = 0;

    public float getHeight() {
        return mHeight;
    }

    protected void setHeight(float height) {
        if (height > 0) mHeight = height;
    }

    //TODO Animation

    public Element() {
        mId = ++mIdCount;
    }

    public Element(Element e) {
        mId = ++mIdCount;
        mWidth = e.getWidth();
        mHeight = e.getHeight();
        mGenTool = e.getGenTool();
        mDefaultPaint = e.getDefaultPaint();
        mPureWidth = e.getPureWidth();
        mPureHeight = e.getPureHeight();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Element) {
            if (mId == ((Element) o).getId()) return true;
        }
        return false;
    }

    protected Paint mDefaultPaint;

    public void setDefaultPaint(Paint p) {
        mDefaultPaint = p;
        setPaint(p);
    }

    public Paint getDefaultPaint() {
        return mDefaultPaint;
    }

    protected Paint mDrawPaint;

    public Paint getDrawPaint() {
        return mDrawPaint;
    }

    protected Paint mErasePaint;

    public Paint getErasePaint() {
        return mErasePaint;
    }

    protected float mPureWidth;

    public void setPureWidth(float pureWidth) {
        mPureWidth = pureWidth;
    }

    public float getPureWidth() {
        return mPureWidth;
    }

    protected float mPureHeight;

    public void setPureHeight(float pureHeight) {
        mPureWidth = pureHeight;
    }

    public float getPureHeight() {
        return mPureHeight;
    }

    public void setPaint(Paint p) {
        mDrawPaint = new Paint(p);
        mErasePaint = new Paint(p);
        mErasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        setWidth(mPureWidth + mDrawPaint.getStrokeWidth());
        setHeight(mPureHeight + mDrawPaint.getStrokeWidth());
    }

}
