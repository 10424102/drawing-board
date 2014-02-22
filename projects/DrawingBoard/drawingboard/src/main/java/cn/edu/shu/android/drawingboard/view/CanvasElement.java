package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.edu.shu.android.drawingboard.core.elements.Element;

/**
 * Created by yy on 2/21/14.
 */
public class CanvasElement extends View {
    private Element content;
    private Bitmap buffer;
    private Canvas bufCanvas;
    private float width;
    private float height;

    public void setContent(Element e) {
        content = e;
        float paintSize = (float) Math.ceil(e.getPaint().getStrokeWidth());
        width = e.getWidth() + paintSize;
        height = e.getHeight() + paintSize;
        buffer = Bitmap.createBitmap((int) (width + 0.5), (int) (height + 0.5), Bitmap.Config.ARGB_8888);
        bufCanvas = new Canvas(buffer);
        content.paint(bufCanvas);
    }

    private void init() {
        final CanvasElement element = this;
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CanvasElement e = (CanvasElement) v;
                //TranslateAnimation anim = new TranslateAnimation(0,100,0,100);
                //AlphaAnimation anim = new AlphaAnimation(1,0);
//                RotateAnimation anim = new RotateAnimation(0,30);
//                anim.setDuration(1000);
//                anim.setFillAfter(true);
//
//                final RotateAnimation anim2 = new RotateAnimation(30,0);
//                anim2.setDuration(1000);
//                anim2.setFillAfter(true);
//                final Handler handler = new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        if(msg.what==0x123){
//                            element.startAnimation(anim2);
//                        }
//                        else if(msg.what==0x124){
//                            ScaleAnimation anim = new ScaleAnimation(1,2,1,(float)2.5);
//                            anim.setDuration(1000);
//                            element.startAnimation(anim);
//                        }
//                    }
//                };
//                v.startAnimation(anim);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        handler.sendEmptyMessage(0x123);
//                    }
//                },1000);
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        handler.sendEmptyMessage(0x124);
//                    }
//                },2000);

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    if (content.inside(event.getX(), event.getY())) {
                        Paint p = new Paint();
                        e.getCanvas().drawRect(1, 1, width - 1, height - 1, p);
                        e.invalidate();
                    }
                }

                return false;
            }
        });
    }

    public CanvasElement(Context context) {
        super(context);
        init();
    }

    public CanvasElement(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CanvasElement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        //int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) (width + 0.5), (int) (height + 0.5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(buffer, 0, 0, null);
    }

    public Canvas getCanvas() {
        return bufCanvas;
    }
}
