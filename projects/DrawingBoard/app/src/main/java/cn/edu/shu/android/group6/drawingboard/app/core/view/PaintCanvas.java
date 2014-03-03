package cn.edu.shu.android.group6.drawingboard.app.core.view;

import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yy on 2/27/14.
 */
public class PaintCanvas extends ViewGroup {
    private Draft draft;
    private Mirror mirror;
    private Artwork artwork;
    private List<CanvasElement> selectedElements = new ArrayList<>();
    private AnimatorSet animatorSet = new AnimatorSet();

    public AnimatorSet getAnimatorSet() {
        return animatorSet;
    }

    public void setAnimatorSet(AnimatorSet animatorSet) {
        this.animatorSet = animatorSet;
    }

    public Draft getDraft() {
        return draft;
    }

    public Mirror getMirror() {
        return mirror;
    }

    public Artwork getArtwork() {
        return artwork;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(parentWidth, parentHeight);
    }

    public PaintCanvas(Context context) {
        super(context);
        draft = new Draft(context);
        //draft.setBackgroundColor(Color.argb(30, 0, 255, 0));
        mirror = new Mirror(context);
        //mirror.setBackgroundColor(Color.RED);
        artwork = new Artwork(context);
        //artwork.setBackgroundColor(Color.RED);
        addView(artwork);
        addView(mirror);
        addView(draft);
    }

    public List<CanvasElement> getSelectedElements() {
        return selectedElements;
    }

    public void mirror() {
//        try {
//            CanvasElement selected = selectedElements.get(0);
//            CanvasElement copy = (CanvasElement)selected.clone();
//            selected.setVisibility(INVISIBLE);
//            //mirror.addView(copy);
//            TextView text = new TextView(getContext());
//            text.setText("message");
//            mirror.addView(text);
//            copy.setBackgroundColor(Color.RED);
//        } catch (CloneNotSupportedException e) {
//            e.printStackTrace();
//        }
    }
}
