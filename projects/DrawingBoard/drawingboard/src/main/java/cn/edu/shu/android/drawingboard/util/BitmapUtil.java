package cn.edu.shu.android.drawingboard.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import cn.edu.shu.android.drawingboard.MyApplication;

/**
 * Created by yy on 1/26/14.
 */
public class BitmapUtil {
    private static MyApplication app = MyApplication.getInstance();

    public static Bitmap getBitmapFile(String path, double width, double height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = Math.max((int) Math.round(options.outHeight / height), (int) Math.round(options.outWidth / width));
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path);
    }

    public static Bitmap getBitmapFile(String path) {
        return BitmapFactory.decodeFile(path);
    }

    public static Bitmap getBitmapResource(int resourceId) {
        return BitmapFactory.decodeResource(app.getResources(), resourceId);
    }

    public static Bitmap getBitmapResource(int resourceId, double width, double height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(app.getResources(), resourceId, options);
        options.inSampleSize = Math.max((int) Math.round(options.outHeight / height), (int) Math.round(options.outWidth / width));
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(app.getResources(), resourceId, options);
    }

    public static Point getBitmapSize(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return new Point(options.outWidth, options.outHeight);
    }

}
