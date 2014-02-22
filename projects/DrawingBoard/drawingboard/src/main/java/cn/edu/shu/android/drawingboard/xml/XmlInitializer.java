package cn.edu.shu.android.drawingboard.xml;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by yy on 2/6/14.
 */
public class XmlInitializer {

    public static Paint getPaint(Block block) {
        Map<String, Paint.Style> map = new HashMap<>();
        map.put("fill", Paint.Style.FILL);
        map.put("fill_and_stroke", Paint.Style.FILL_AND_STROKE);
        map.put("stroke", Paint.Style.STROKE);

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
                    p.setStyle(map.get(a.getValue()));
                    break;
                case "xfermode":
                    p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    break;
                case "alpha":
                    p.setAlpha(Integer.parseInt(a.getValue()));
                    break;
                case "anti-alias":
                    p.setAntiAlias(Boolean.parseBoolean(a.getValue()));
                    break;
                case "color-filter":
                    break;
                case "dither":
                    break;
                case "shader":
                    break;

            }
        }
        return p;
    }
}
