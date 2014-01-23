package cn.edu.shu.android.drawingboard.core.elements;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.Position;
import cn.edu.shu.android.drawingboard.core.exception.ParserXMLException;
import cn.edu.shu.android.drawingboard.core.transform.Transform;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;

/**
 * Created by yy on 1/22/14.
 */
public class Point extends Element {
    Paint pen;

    public Point() {
        setXMLBlockName("point");
    }

    public Paint getPen() {

        return pen;
    }

    public void setPen(Paint pen) {
        this.pen = pen;
    }

    @Override
    public void loadXML(Block root) throws ParserXMLException {
        if (root.getName().equalsIgnoreCase("point")) {
            pen = new Paint();

            //iterate attributes
            for (Iterator<Attr> i = root.attrIterator(); i.hasNext(); ) {
                Attr a = i.next();
                switch (a.getName().toLowerCase()) {
                    case "size":
                        try {
                            pen.setStrokeWidth(Float.parseFloat(a.getValue()));
                        } catch (NumberFormatException e) {
                            throw new ParserXMLException("Value syntax error: point -> size.");
                        }
                        break;
                    case "color":
                        try {
                            pen.setColor(Color.parseColor(a.getValue()));
                        } catch (IllegalArgumentException e) {
                            throw new ParserXMLException("Value syntax error: point -> color.");
                        }
                        break;
                    case "style":
                        if (a.getValue().equalsIgnoreCase("FILL")) {
                            pen.setStyle(Paint.Style.FILL);
                        } else if (a.getValue().equalsIgnoreCase("STROKE")) {
                            pen.setStyle(Paint.Style.STROKE);
                        } else {
                            throw new ParserXMLException("Value syntax error: point -> style.");
                        }
                        break;
                    default:
                        throw new ParserXMLException("< point > only support attributes: size, color and style.");
                }
            }

            //iterate sub blocks, center
            for (Iterator<Block> i = root.blockIterator(); i.hasNext(); ) {
                Block e = i.next();
                switch (e.getName().toLowerCase()) {
                    case "center":
                        center = new Position();
                        center.loadXML(e);
                        break;
                    default:
                        throw new ParserXMLException("< point > only support element center.");
                }
            }
        }
        throw new ParserXMLException("Try to use a non-point xml to initialize the Point object.");
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
