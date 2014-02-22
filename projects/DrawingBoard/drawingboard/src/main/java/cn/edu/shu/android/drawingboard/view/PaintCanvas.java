package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.R;

/**
 * Created by yy on 2/21/14.
 */
public class PaintCanvas extends ViewGroup {
    private static final MyApplication app = MyApplication.getInstance();
    private int width;
    private int height;
    private Draft draft;
    private Artwrok artwrok;
    private OnTouchListener defaultOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };

    public PaintCanvas(Context context) {
        super(context);
        artwrok = new Artwrok(context);
        draft = new Draft(context);
        addView(artwrok);
        addView(draft);
        app.registerPaintCanvas(this);
        draft.setPaintCanvas(this);
    }

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PaintCanvas,
                0, 0
        );
        try {
            width = a.getInteger(R.styleable.PaintCanvas_width, 0);
            height = a.getInteger(R.styleable.PaintCanvas_height, 0);
        } finally {
            a.recycle();
        }
        artwrok = new Artwrok(context);
        draft = new Draft(context);
        addView(artwrok);
        addView(draft);
        app.registerPaintCanvas(this);
        draft.setPaintCanvas(this);
    }

    public PaintCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PaintCanvas,
                0, 0
        );
        try {
            width = a.getInteger(R.styleable.PaintCanvas_width, 0);
            height = a.getInteger(R.styleable.PaintCanvas_height, 0);
        } finally {
            a.recycle();
        }
        artwrok = new Artwrok(context);
        draft = new Draft(context);
        addView(artwrok);
        addView(draft);
        app.registerPaintCanvas(this);
        draft.setPaintCanvas(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //System.out.println(l + "," + t + "," + r + "," + b);
        draft.layout(0, 0, draft.getMeasuredWidth(), draft.getMeasuredHeight());
        artwrok.layout(0, 0, artwrok.getMeasuredWidth(), artwrok.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //System.out.println(widthMeasureSpec + "," + heightMeasureSpec);
        draft.measure(widthMeasureSpec, heightMeasureSpec);
        artwrok.measure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void add(CanvasElement e) {
        Log.i("yy", "add");
        artwrok.addView(e);
        //artwrok.invalidate();
        artwrok.requestLayout();
    }

    public void clear() {
        artwrok.clear();
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        draft.setOnTouchListener(l);
        //super.setOnTouchListener(l);
    }

    public Canvas getCanvas() {
        return draft.getCanvas();
    }

    @Override
    public void invalidate() {
        draft.invalidate();
    }
}
