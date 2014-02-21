package cn.edu.shu.android.drawingboard.xml;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yy on 2/6/14.
 */
public class XmlInitializer {

    public static Paint getPaint(Block block) {
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
}
