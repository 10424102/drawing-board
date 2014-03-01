package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

import cn.edu.shu.android.group6.drawingboard.app.App;

/**
 * Created by yy on 3/2/14.
 */
public class Shade extends View {
    private static final App app = App.getInstance();
    private Paint paint = new Paint();
//    private boolean open = false;
//
//    public boolean isOpen() {
//        return open;
//    }
//
//    public void setOpen(boolean open) {
//        this.open = open;
//    }

    public Shade(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        if (open) {
//            Bitmap bg = BitmapUtil.loadBitmapFromView(app.getPaintCanvas().getArtwork());
//            //Bitmap bg = BitmapFactory.decodeResource(app.getResources(), R.drawable.test);
//            Bitmap blur = BitmapUtil.fastblur(bg, 10);
//            //Canvas c = new Canvas(bg);
//            //c.scale(0.5f,0.5f);
//            canvas.drawBitmap(blur, 0, 0, paint);
//        } else {
////            super.draw(canvas);
////            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
////            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
////            paint.setXfermode(null);
//        }
//    }
}
