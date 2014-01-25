package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.exception.BuildElementException;
import cn.edu.shu.android.drawingboard.core.transform.Transform;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;

public class Point extends Element {
    private Paint pen;

    public Point(Block b,int zindex)throws BuildElementException{
        super(b);
        this.setZindex(zindex);
        pen = new Paint();
        loadXML(b);
    }

    private void setPenSize(String s) throws BuildElementException{
        try{
            pen.setStrokeWidth(Float.parseFloat(s));
        }catch (Exception e){
            throw new BuildElementException(e);
        }
    }

    private void setPenColor(String s) throws BuildElementException{
        try{
            pen.setColor(Color.parseColor(s));
        }catch (Exception e){
            throw new BuildElementException(e);
        }
    }

    private void setPenStyle(String s) throws BuildElementException{
        switch(s.toUpperCase())
        {
            case "FILL":
                pen.setStyle(Paint.Style.FILL);
                break;
            case "STROKE":
                pen.setStyle(Paint.Style.STROKE);
                break;
            default:
                throw new BuildElementException("Unknown pen style.");
        }
    }


    @Override
    public void loadXML(Block root) throws BuildElementException {
        if (root.getName().equalsIgnoreCase("point")) {
            for (Iterator<Attr> i = root.attrIterator(); i.hasNext(); ) {
                Attr a = i.next();
                switch (a.getName().toLowerCase()) {
                    case "size":
                        setPenSize(a.getValue());
                        break;
                    case "color":
                        setPenColor(a.getValue());
                        break;
                    case "style":
                        setPenStyle(a.getValue());
                        break;
                }
            }
            for (Iterator<Block> i = root.blockIterator(); i.hasNext(); ) {
                Block b = i.next();
                switch (b.getName().toLowerCase()) {
                    case "center":
                        setCenter(b);
                        break;
                }
            }
        }
    }

    @Override
    public void draw() {
        canvas.drawPoint(center.getX(), center.getY(), pen);
    }

    @Override
    public void transform(Transform t) {

    }

    @Override
    public void generate(View v) {

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        center.setX(event.getX());
                        center.setY(event.getY());
                        draw();
                        MyApplication app = MyApplication.getInstance();
                        app.pc.invalidate();
                        break;
                }

                return true;//后面的touch事件继续发送到该方法
            }
        });
    }
}
