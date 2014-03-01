package cn.edu.shu.android.group6.drawingboard.app.util;

import android.util.DisplayMetrics;

import cn.edu.shu.android.group6.drawingboard.app.App;

/**
 * Created by yy on 2/27/14.
 */
public class DimensionUtil {
    private static final App app = App.getInstance();

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = app.getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = app.getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }
}
