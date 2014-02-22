package cn.edu.shu.android.drawingboard.core.elements.inherited;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.GenerationSlide;
import cn.edu.shu.android.drawingboard.core.elements.Position;
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

    public int getPictureWidth() {
        return pictureWidth;
    }

    public int getPictureHeight() {
        return pictureHeight;
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
    public Position measure(float startX, float startY, float endX, float endY) {
        width = pictureWidth + PADDING;
        height = pictureHeight + PADDING;
        centerX = width / 2;
        centerY = height / 2;
        return new Position(endX - centerX, endY - centerY);
    }

    @Override
    public void paint(Canvas canvas) {
        getPicture();
        canvas.drawBitmap(bmp, (width - pictureWidth) / 2, (height - pictureHeight) / 2, paint);
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
            public void onActionMove(StaticPicture e, float x, float y, Canvas canvas, float startX, float startY, float prevX, float prevY) {
                canvas.drawBitmap(e.getPicture(), x - e.getPictureWidth() / 2, y - e.getPictureHeight() / 2, e.getPaint());
            }

            @Override
            public void onActionUp(StaticPicture e, float x, float y, float startX, float startY) {
                //e.getPicture().recycle();
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
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean inside(float x, float y) {
        return true;
    }
}
