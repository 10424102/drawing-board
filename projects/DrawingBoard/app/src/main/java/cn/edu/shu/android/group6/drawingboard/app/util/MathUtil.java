package cn.edu.shu.android.group6.drawingboard.app.util;

/**
 * Created by yy on 3/1/14.
 */
public class MathUtil {
    public static float distance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}
