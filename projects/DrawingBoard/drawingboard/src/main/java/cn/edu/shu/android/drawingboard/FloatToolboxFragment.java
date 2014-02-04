package cn.edu.shu.android.drawingboard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by yy on 1/26/14.
 */
public class FloatToolboxFragment extends Fragment {

    private boolean moving = false;
    private int top = 100;
    private int left = 200;
    private View fragmentRootView;
    private FrameLayout.LayoutParams lp;
    private float startX;
    private float startY;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRootView = inflater.inflate(R.layout.fragment_float_toolbox, container, false);
        lp = new FrameLayout.LayoutParams(200, 50);
        lp.setMargins(top, left, 0, 0);
        fragmentRootView.setLayoutParams(lp);
        if (fragmentRootView != null) {
            fragmentRootView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    return false;
                }
            });
            fragmentRootView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            moving = true;
                            startX = event.getRawX();
                            startY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (moving == true) {
                                float dx = event.getRawX() - startX;
                                float dy = event.getRawY() - startY;
                                startX = event.getRawX();
                                startY = event.getRawY();
                                lp.setMargins(top, left, 0, 0);
                                fragmentRootView.setLayoutParams(lp);
                                fragmentRootView.invalidate();
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            moving = false;
                            break;
                    }
                    return true;
                }
            });
        }
        return fragmentRootView;
    }


}
