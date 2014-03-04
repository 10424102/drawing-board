package cn.edu.shu.android.group6.drawingboard.app.util;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yy on 3/4/14.
 */
public class ListenerUtil {
    public static View.OnTouchListener floatListener = new View.OnTouchListener() {
        float dx = 0;
        float dy = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dx = event.getRawX() - v.getX();
                    dy = event.getRawY() - v.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setX(event.getRawX() - dx);
                    v.setY(event.getRawY() - dy);
                    break;
            }
            return true;
        }
    };
}
