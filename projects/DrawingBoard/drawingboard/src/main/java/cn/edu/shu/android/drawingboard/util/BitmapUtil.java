package cn.edu.shu.android.drawingboard.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.R;

/**
 * Created by yy on 1/26/14.
 */
public class BitmapUtil {
    private static MyApplication app = MyApplication.getInstance();

    public static Bitmap getBitmap(int resourceId, double size) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeResource(app.getResources(), R.drawable.default_tool_icon, opts);
        opts.inSampleSize = (int) (Math.max(opts.outHeight, opts.outWidth) / size + 0.5);
        opts.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(app.getResources(), R.drawable.default_tool_icon, opts);
        return bmp;
    }
}
