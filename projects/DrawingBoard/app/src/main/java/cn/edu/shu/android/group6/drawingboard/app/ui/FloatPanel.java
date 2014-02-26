package cn.edu.shu.android.group6.drawingboard.app.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.shu.android.group6.drawingboard.app.R;

/**
 * Created by yy on 2/23/14.
 */
public class FloatPanel extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_float_panel, container, false);
        if (root != null) {
            root.setOnTouchListener(new View.OnTouchListener() {
                float px;
                float py;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        px = event.getRawX();
                        py = event.getRawY();
                    } else if (action == MotionEvent.ACTION_MOVE) {
                        float dx = event.getRawX() - px;
                        float dy = event.getRawY() - py;
                        v.setX(v.getX() + dx);
                        v.setY(v.getY() + dy);
                        px = event.getRawX();
                        py = event.getRawY();
                    } else if (action == MotionEvent.ACTION_UP) {

                    }
                    return true;
                }
            });
        }
        return root;
    }
}
