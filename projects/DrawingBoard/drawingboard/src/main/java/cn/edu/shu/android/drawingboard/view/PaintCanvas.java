package cn.edu.shu.android.drawingboard.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

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
    private Mirror mirror;
    private OnTouchListener defaultOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    };
    private List<CanvasElement> selectedCanvasElementList = new ArrayList<>();
    private boolean multiSelect = true;

    private void init(Context context) {
        artwrok = new Artwrok(context);
        mirror = new Mirror(context);
        draft = new Draft(context);
        addView(artwrok);
        addView(mirror);
        addView(draft);
        artwrok.setPaintCanvas(this);
        draft.setPaintCanvas(this);
        mirror.setPaintCanvas(this);
        app.registerPaintCanvas(this);
    }

    public PaintCanvas(Context context) {
        super(context);
        init(context);
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
        init(context);
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
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
//        draft.layout(0, 0, draft.getMeasuredWidth(), draft.getMeasuredHeight());
//        mirror.layout(0, 0, draft.getMeasuredWidth(), draft.getMeasuredHeight());
//        artwrok.layout(0, 0, artwrok.getMeasuredWidth(), artwrok.getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
        if (width == 0 || height == 0) {
            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(width, height);
    }

    public void add(CanvasElement e) {
        Log.i("yy", "add");
        artwrok.addView(e);
        e.setPaintCanvas(this);
        //artwrok.invalidate();
        artwrok.requestLayout();
    }

    public void clear() {
        if (selectedCanvasElementList.isEmpty()) {
            artwrok.clear();
        } else {
            for (CanvasElement e : selectedCanvasElementList) {
                artwrok.removeView(e);
            }
            selectedCanvasElementList.clear();
        }
    }

    public void bringCanvasElementToFront() {
        for (CanvasElement e : selectedCanvasElementList) {
            artwrok.bringChildToFront(e);
        }
        artwrok.requestLayout();
        invalidate();
    }


    @Override
    public void setOnTouchListener(OnTouchListener l) {
        draft.setOnTouchListener(l);
        //super.setOnTouchListener(l);
    }

    public Canvas getCanvas() {
        return draft.getCanvas();
    }

    public void setMultiSelect(boolean b) {
        multiSelect = b;
    }

    public boolean isMultiSelect() {
        return multiSelect;
    }

    @Override
    public void invalidate() {
        draft.invalidate();
    }

    public void endGeneration() {
        draft.setOnTouchListener(defaultOnTouchListener);
    }

    public int getCanvasElementNum() {
        return artwrok.getChildCount();
    }

    public void addSelectedCanvasElement(CanvasElement ce) {
        if (!multiSelect) {
            for (CanvasElement e : selectedCanvasElementList) {
                e.unselect();
            }
        }
        selectedCanvasElementList.add(ce);
    }

    public void removeSelectedCanvasElement(CanvasElement ce) {
        selectedCanvasElementList.remove(ce);
    }

    public void clearSelectedCanvasElement() {
        for (CanvasElement e : selectedCanvasElementList) {
            e.unselect();
        }
    }

    public CanvasElement mirror() {
        CanvasElement select = selectedCanvasElementList.get(0);
        CanvasElement copy = select.copy();
        mirror.shadeOn();
        mirror.addView(copy);
        return copy;
    }
}
