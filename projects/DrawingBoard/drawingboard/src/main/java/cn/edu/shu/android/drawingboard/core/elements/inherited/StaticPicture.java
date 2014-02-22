package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.GenerationSlide;
import cn.edu.shu.android.drawingboard.util.BitmapUtil;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlInitializer;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

/**
 * Created by yy on 2/19/14.
 */
public class StaticPicture extends Element implements XmlInitializable {
    private String picturePath;
    private Bitmap bmp;
    private int pictureWidth;
    private int pictureHeight;
    private float left;
    private float top;

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public int getPictureWidth() {
        return pictureWidth;
    }

    public int getPictureHeight() {
        return pictureHeight;
    }

    public void setLeftTop(float l, float t) {
        left = l;
        top = t;
    }

    public Bitmap getPicture() {
        if (bmp == null || bmp.isRecycled()) {
            bmp = BitmapUtil.getBitmapFile(picturePath);
        }
        return bmp;
    }

    public void setPicturePath(String path) {
        //TODO Test if the file exists
        picturePath = path;
    }

    public String getPicturePath() {
        return picturePath;
    }

    //Constructor
    public StaticPicture() {

    }

    public StaticPicture(StaticPicture x) {
        super(x);
        picturePath = x.getPicturePath();
        pictureHeight = x.getPictureHeight();
        pictureWidth = x.getPictureWidth();
    }

    @Override
    public void measure(float startX, float startY, float endX, float endY) {
        center.set(endX, endY);
    }

    @Override
    public void paint(Canvas canvas) {
        getPicture();
        canvas.drawBitmap(bmp, left, top, getPaint());
        bmp.recycle();
    }

    @Override
    public void generate() {
        GenerationSlide<StaticPicture> gen = new GenerationSlide<>(StaticPicture.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<StaticPicture>() {
            @Override
            public void onActionDown(StaticPicture e, float x, float y, Canvas canvas) {
                canvas.drawBitmap(e.getPicture(), x - e.getPictureWidth() / 2, y - e.getPictureHeight() / 2, e.getPaint());
            }

            @Override
            public void onActionMove(StaticPicture e, float x, float y, Canvas canvas, float startX, float startY) {
                canvas.drawBitmap(e.getPicture(), x - e.getPictureWidth() / 2, y - e.getPictureHeight() / 2, e.getPaint());
            }

            @Override
            public void onActionUp(StaticPicture e, float x, float y, float startX, float startY) {
                e.getPicture().recycle();
                e.setLeftTop(x - e.getPictureWidth() / 2, y - e.getPictureHeight() / 2);
            }
        });
        app.setPaintCanvasOnTouchListener(gen);
    }

    @Override
    public boolean xmlParse(Block block) throws XmlParserException {
        for (Iterator i = block.attrIterator(); i.hasNext(); ) {
            Attr a = (Attr) i.next();
            switch (a.getName().toLowerCase()) {
                case "path":
                    picturePath = genTool.getDirPath() + a.getValue();
                    Point size = BitmapUtil.getBitmapSize(picturePath);
                    pictureWidth = size.x;
                    pictureHeight = size.y;
                    break;
            }
        }
        for (Iterator i = block.blockIterator(); i.hasNext(); ) {
            Block b = (Block) i.next();
            switch (b.getName().toLowerCase()) {
                case "paint":
                    setPaint(XmlInitializer.getPaint(b));
                    break;
                case "center":
                    center.xmlParse(b);
                    break;
            }
        }
        return true;
    }
}
