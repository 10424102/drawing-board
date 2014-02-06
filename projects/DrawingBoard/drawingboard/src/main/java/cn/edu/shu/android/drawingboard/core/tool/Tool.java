package cn.edu.shu.android.drawingboard.core.tool;

import android.graphics.Paint;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.elements.Element;

public class Tool {
    private static int mIdCount = 0;
    private static final MyApplication app = MyApplication.getInstance();

    private Element mContent;

    public Element getContent() {
        return mContent;
    }

    public void setContent(Element mContent) {
        this.mContent = mContent;
    }

    private String mIconPath;

    public String getIconPath() {
        return mIconPath;
    }

    public void setIconPath(String mIconPath) {
        this.mIconPath = mIconPath;
    }

    private String mName;

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    private int mId;

    public int getId() {
        return mId;
    }

    public Tool() {
        mId = ++mIdCount;
    }

    public void startUsing() {
        app.setCurrentTool(this);
        mContent.generate(app.getPaintCanvas());
    }

    public Paint getDrawPaint() {
        return mContent.getDrawPaint();
    }

    public Paint getErasePaint() {
        return mContent.getErasePaint();
    }
}
