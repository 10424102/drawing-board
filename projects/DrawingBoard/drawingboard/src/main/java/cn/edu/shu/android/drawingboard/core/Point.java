package cn.edu.shu.android.drawingboard.core;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import org.dom4j.Attribute;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.MyApplication;

/**
 * Created by yy on 1/22/14.
 */
public class Point extends Element {
    Paint pen;

    public void setPen(Paint pen) {
        this.pen = pen;
    }

    public Paint getPen() {

        return pen;
    }

    @Override
    public void loadXML(org.dom4j.Element root) throws PhraseXMLException {
        if (!root.getName().toLowerCase().equals("point")) {
            throw new PhraseXMLException("Try to use a non-point xml to initialize the Point object.");
        }
        pen = new Paint();
        for (Iterator i = root.attributeIterator(); i.hasNext(); ) {
            Attribute a = (Attribute) i.next();
            if (a.getName().equals("size")) {
                try {
                    pen.setStrokeWidth(Float.parseFloat(a.getValue()));
                } catch (NumberFormatException e) {
                    throw new PhraseXMLException("Value syntax error: point -> size.");
                }
            } else if (a.getName().equals("color")) {
                try{
                    pen.setColor(Color.parseColor(a.getValue()));
                }catch (IllegalArgumentException e){
                    throw new PhraseXMLException("Value syntax error: point -> color.");
                }
            } else if (a.getName().equals("style")) {
                if(a.getValue().toUpperCase().equals("FILL")){
                    pen.setStyle(Paint.Style.FILL);
                }else if(a.getValue().toUpperCase().equals("STROKE")){
                    pen.setStyle(Paint.Style.STROKE);
                }
                else{
                    throw new PhraseXMLException("Value syntax error: point -> style.");
                }
            } else {
                throw new PhraseXMLException("Point only support attributes: size, color and style.");
            }
        }

        if (root.attributeValue("size") != null) {
            try {
                pen.setStrokeWidth(Float.parseFloat(root.attributeValue("size")));
            } catch (NumberFormatException e) {
                throw new PhraseXMLException("Syntax error: point -> size.");

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
                switch (event.getAction()){
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
