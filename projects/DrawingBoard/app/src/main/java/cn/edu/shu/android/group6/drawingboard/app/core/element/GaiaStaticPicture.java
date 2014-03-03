package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.Iterator;

import cn.edu.shu.android.group6.drawingboard.app.core.XmlInitializable;
import cn.edu.shu.android.group6.drawingboard.app.core.tool.Tool;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Draft;
import cn.edu.shu.android.group6.drawingboard.app.util.BitmapUtil;

/**
 * Created by yy on 3/3/14.
 */
public class GaiaStaticPicture extends Gaia implements XmlInitializable {
    public GaiaStaticPicture(GaiaStaticPicture g) {
        super(g);
        this.picPath = g.picPath;
        this.picBitmap = g.picBitmap;
    }

    private static final View.OnTouchListener gen = new View.OnTouchListener() {
        Draft draft;
        Canvas canvas;
        GaiaStaticPicture template;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    draft = app.getPaintCanvas().getDraft();
                    canvas = draft.getCanvas();
                    template = (GaiaStaticPicture) app.getCurrentTool().getGenerator();
                    canvas.save();
                    canvas.translate(event.getX() - template.getWidth() / 2, event.getY() - template.getHeight() / 2);
                    template.paint(draft.getCanvas());
                    canvas.restore();
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    draft.clear();
                    canvas.save();
                    canvas.translate(event.getX() - template.getWidth() / 2, event.getY() - template.getHeight() / 2);
                    template.paint(draft.getCanvas());
                    canvas.restore();
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    draft.clear();
                    draft.invalidate();
                    GaiaStaticPicture gaia = new GaiaStaticPicture(template);
                    gaia.setPicPath(template.getPicPath());
                    gaia.set(app.getPaint(), event.getX(), event.getY());
                    CanvasElement element = new CanvasElement(app.getContext(), gaia);
                    element.setFlags(CanvasElement.SELECT_FLAG);
                    app.getPaintCanvas().getArtwork().addView(element);
                    break;
            }
            return true;
        }
    };

    public GaiaStaticPicture(Tool genTool) {
        super(genTool);
    }

    private String picPath;
    private Bitmap picBitmap;

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
        picBitmap = BitmapUtil.getBitmapFromUrl(picPath);
        setSize(picBitmap.getWidth(), picBitmap.getHeight());
    }

    public void set(Paint p, float centerX, float centerY) {
        setPaint(p);
        setLeftTop(centerX - width / 2, centerY - height / 2);
    }

    @Override
    public void paint(Canvas canvas) {
        if (picBitmap == null) {
            picBitmap = BitmapUtil.getBitmapFromUrl(picPath);
        }
        canvas.drawBitmap(picBitmap, 0, 0, paint);
    }

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(gen);
    }

    @Override
    public void parseXmlElement(Element xmlElement) {
        for (Iterator i = xmlElement.attributeIterator(); i.hasNext(); ) {
            Attribute a = (Attribute) i.next();
            switch (a.getName()) {
                case "path":
                    setPicPath(genTool.getDirPath() + a.getValue());
                    break;
            }
        }
    }
}
