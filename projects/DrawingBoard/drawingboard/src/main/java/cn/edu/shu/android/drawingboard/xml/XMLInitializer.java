package cn.edu.shu.android.drawingboard.xml;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.edu.shu.android.drawingboard.core.elements.Dot;
import cn.edu.shu.android.drawingboard.core.elements.Position;
import cn.edu.shu.android.drawingboard.core.elements.StraightSegment;
import cn.edu.shu.android.drawingboard.core.tool.Tool;

/**
 * Created by yy on 2/6/14.
 */
public class XMLInitializer {

    private static Position getCenter(Block block) {
        Position c = new Position(0, 0);
        for (Iterator i = block.attrIterator(); i.hasNext(); ) {
            Attr a = (Attr) i.next();
            switch (a.getName().toLowerCase()) {
                case "x":
                    c.setX(Float.parseFloat(a.getValue()));
                    break;
                case "y":
                    c.setY(Float.parseFloat(a.getValue()));
                    break;
            }
        }
        return c;
    }

    private static Paint getPaint(Block block) {
        Map<String, Paint.Style> styleMap = new HashMap<>();
        styleMap.put("fill", Paint.Style.FILL);
        styleMap.put("fill_and_stroke", Paint.Style.FILL_AND_STROKE);
        styleMap.put("stroke", Paint.Style.STROKE);

        Paint p = new Paint();
        for (Iterator i = block.attrIterator(); i.hasNext(); ) {
            Attr a = (Attr) i.next();
            switch (a.getName().toLowerCase()) {
                case "color":
                    p.setColor(Color.parseColor(a.getValue()));
                    break;
                case "stroke-width":
                    p.setStrokeWidth(Float.parseFloat(a.getValue()));
                    break;
                case "style":
                    p.setStyle(styleMap.get(a.getValue()));
                    break;
            }
        }
        return p;
    }

    public static void init(Object x, Block block) {
        if (x instanceof Dot) {
            Dot y = (Dot) x;
            for (Iterator i = block.blockIterator(); i.hasNext(); ) {
                Block b = (Block) i.next();
                switch (b.getName().toLowerCase()) {
                    case "paint":
                        y.setDefaultPaint(getPaint(b));
                        break;
                    case "center":
                        break;
                }
            }
        } else if (x instanceof StraightSegment) {
            StraightSegment y = (StraightSegment) x;
            float length = 0;
            float angle = 0;
            for (Iterator i = block.attrIterator(); i.hasNext(); ) {
                Attr a = (Attr) i.next();
                switch (a.getName().toLowerCase()) {
                    case "length":
                        length = Float.parseFloat(a.getValue());
                        break;
                    case "angle"://anti-clockwise [0,360)
                        angle = Float.parseFloat(a.getValue());
                        break;
                }
            }
            angle = (float) Math.PI * angle / 180;
            length = length / 2;
            float sin = (float) (length * Math.sin((double) angle));
            float cos = (float) (length * Math.cos((double) angle));
            y.setStartPosition(new Position(-sin, -cos));
            y.setStartPosition(new Position(sin, cos));
            for (Iterator i = block.blockIterator(); i.hasNext(); ) {
                Block b = (Block) i.next();
                switch (b.getName().toLowerCase()) {
                    case "paint":
                        y.setDefaultPaint(getPaint(b));
                        break;
                    case "center":
                        break;
                }
            }

        } else if (x instanceof Tool) {
            Tool y = (Tool) x;
            if (block.getName().equalsIgnoreCase("tool")) {
                for (Iterator i = block.attrIterator(); i.hasNext(); ) {
                    Attr a = (Attr) i.next();
                    switch (a.getName().toLowerCase()) {
                        case "name":
                            y.setName(a.getValue());
                            break;
                        case "icon-path":
                            y.setIconPath(a.getValue());
                            break;
                    }
                }
                for (Iterator i = block.blockIterator(); i.hasNext(); ) {
                    Block b = (Block) i.next();
                    switch (b.getName().toLowerCase()) {
                        case "element":
                            Block bb = b.getFirstSubBlock();
                            switch (bb.getName().toLowerCase()) {
                                case "dot":
                                    Dot dot = new Dot();
                                    init(dot, bb);
                                    y.setContent(dot);
                                    break;
                                case "straight-segment":
                                    StraightSegment straightSegment = new StraightSegment();
                                    init(straightSegment, bb);
                                    y.setContent(straightSegment);
                                    break;
                            }
                            break;
                        case "element-group":
                            break;
                    }
                }
            }
        }
    }
}
