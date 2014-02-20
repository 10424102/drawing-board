package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.core.PaintCanvas;
import cn.edu.shu.android.drawingboard.core.XmlInitializable;
import cn.edu.shu.android.drawingboard.util.BitmapUtil;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;
import cn.edu.shu.android.drawingboard.xml.XmlInitializer;
import cn.edu.shu.android.drawingboard.xml.XmlParserException;

/**
 * Created by yy on 2/19/14.
 */
public class StaticPicture extends Element implements XmlInitializable {
    String picturePath;
    Bitmap bmp;

    public Bitmap getBmp() {
        if (bmp == null || bmp.isRecycled()) {
            bmp = BitmapFactory.decodeFile(picturePath);
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
    }

    @Override
    public void measure(float startX, float startY, float endX, float endY) {
        center.set(endX, endY);
    }

    @Override
    public void paint(Canvas canvas, Paint paint) {
        super.paint(canvas, paint);
        bmp = getBmp();
        canvas.drawBitmap(bmp, (width - pureWidth) / 2, (height - pureHeight) / 2, drawPaint);
        bmp.recycle();
    }

    @Override
    public void generate(View v) {
        GenerationSlide<StaticPicture> gen = new GenerationSlide<>(StaticPicture.class);
        gen.setGenerationSlideListener(new GenerationSlide.GenerationSlideListener<StaticPicture>() {
            @Override
            public void onActionDown(StaticPicture e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint) {
                pc.getCanvas().drawBitmap(e.getBmp(), x - e.getPureWidth() / 2, y - e.getPureHeight() / 2, drawPaint);
                pc.invalidate();
            }

            @Override
            public void onActionMove(StaticPicture e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawBitmap(e.getBmp(), endX - e.getPureWidth() / 2, endY - e.getPureHeight() / 2, erasePaint);
                pc.getCanvas().drawBitmap(e.getBmp(), x - e.getPureWidth() / 2, y - e.getPureHeight() / 2, drawPaint);
                pc.invalidate();
            }

            @Override
            public void onActionUp(StaticPicture e, float x, float y, PaintCanvas pc, Paint drawPaint, Paint erasePaint, float startX, float startY, float endX, float endY) {
                pc.getCanvas().drawBitmap(e.getBmp(), endX - e.getPureWidth() / 2, endY - e.getPureHeight() / 2, erasePaint);
                pc.invalidate();
                e.getBmp().recycle();
            }
        });
        v.setOnTouchListener(gen);
    }

    @Override
    public boolean xmlParse(Block block) throws XmlParserException {
        for (Iterator i = block.attrIterator(); i.hasNext(); ) {
            Attr a = (Attr) i.next();
            switch (a.getName().toLowerCase()) {
                case "path":
                    picturePath = genTool.getDirPath() + a.getValue();
                    Point size = BitmapUtil.getPictureSize(picturePath);
                    pureWidth = size.x;
                    pureHeight = size.y;
                    calculateRealSize();
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
}
