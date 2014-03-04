package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.R;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.ControlArc;
import cn.edu.shu.android.group6.drawingboard.app.core.view.ControlPoint;
import cn.edu.shu.android.group6.drawingboard.app.core.view.PaintCanvas;

/**
 * Created by yy on 3/1/14.
 */
public class TranslateAnimationTool implements Generable {
    private static final App app = App.getInstance();
    private static final PaintCanvas paintCanvas = app.getPaintCanvas();
    private static final SettingPanel settingPanel = new SettingPanel();


    @Override
    public void generate() {

        //get the first selected element
        List<CanvasElement> elements = paintCanvas.getSelectedElements();
        if (elements.isEmpty()) return;
        final CanvasElement selected = elements.get(0);

        //get the copy of the element
        final CanvasElement copy = selected.copy();

        //hide original element
        selected.setVisibility(View.INVISIBLE);

        //open mirror's shade
        paintCanvas.getMirror().shadeOn();

        //add copy to mirror
        paintCanvas.getMirror().addView(copy);

        //highlight copy
        copy.setBackgroundColor(Color.WHITE);

        //add the first control point on the center of the copy
        final ControlPoint controlPoint = new ControlPoint(app.getContext(), copy);
        controlPoint.setX(copy.getGaia().getWidth() / 2 + copy.getX() - ControlPoint.SIZE / 2);
        controlPoint.setY(copy.getGaia().getHeight() / 2 + copy.getY() - ControlPoint.SIZE / 2);

        //set the first CP's on touch event: move -> gen a control-arc & a CP
        controlPoint.setOnTouchListener(new View.OnTouchListener() {
            float viewSx;
            float viewSy;
            Date startTime;
            Date endTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:

                        //record the start time
                        startTime = new Date();

                        //record view's start position
                        viewSx = copy.getX();
                        viewSy = copy.getY();

                        break;

                    case MotionEvent.ACTION_MOVE:

                        break;

                    case MotionEvent.ACTION_UP:

                        //record the end time
                        endTime = new Date();

                        //calculate the duration (ms)
                        final long interval = endTime.getTime() - startTime.getTime();

                        //disable the first cp
                        v.setOnTouchListener(null);

                        //get the first cp
                        final ControlPoint cp1 = (ControlPoint) v;

                        //get the first cp's relevant canvas element
                        final CanvasElement element = cp1.getElement();

                        //add second cp
                        final ControlPoint cp2 = new ControlPoint(app.getContext(), element);
                        cp2.setX(v.getX() + event.getX() - ControlPoint.SIZE / 2);
                        cp2.setY(v.getY() + event.getY() - ControlPoint.SIZE / 2);
                        cp2.setOnTouchListener(new View.OnTouchListener() {
                            float dx = 0;
                            float dy = 0;

                            @Override
                            public boolean onTouch(View v, MotionEvent event) {

                                boolean restoreMark = true;
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:
                                        dx = event.getRawX() - v.getX();
                                        dy = event.getRawY() - v.getY();
                                        break;
                                    case MotionEvent.ACTION_MOVE:
                                        if (restoreMark) {
                                            restoreMark = false;
                                            copy.setX(viewSx);
                                            copy.setY(viewSy);
                                        }
                                        v.setX(event.getRawX() - dx);
                                        v.setY(event.getRawY() - dy);
                                        ((ControlPoint) v).update();
                                        break;
                                    case MotionEvent.ACTION_UP:
                                        ObjectAnimator animX = ObjectAnimator.ofFloat(element, "x", cp1.getCpX() - copy.getWidth() / 2, cp2.getCpX() - copy.getWidth() / 2);
                                        animX.setDuration(interval);
                                        ObjectAnimator animY = ObjectAnimator.ofFloat(element, "y", cp1.getCpY() - copy.getHeight() / 2, cp2.getCpY() - copy.getHeight() / 2);
                                        animY.setDuration(interval);
                                        AnimatorSet anim = new AnimatorSet();
                                        anim.play(animX).with(animY);
                                        settingPanel.setAnimatorSet(anim);
                                        break;
                                }
                                return true;
                            }
                        });
                        app.getPaintCanvas().getMirror().addView(cp2);

                        //add control arc
                        ControlArc controlArc = new ControlArc(app.getContext());
                        controlArc.setFrom(cp1);
                        controlArc.setTo(cp2);
                        app.getPaintCanvas().getMirror().addView(controlArc);

                        //get the delta x,y
                        float ex = event.getX();
                        float ey = event.getY();


                        //gen the translate animation
                        ObjectAnimator animX = ObjectAnimator.ofFloat(element, "x", cp1.getCpX() - copy.getWidth() / 2, cp2.getCpX() - copy.getWidth() / 2);
                        animX.setDuration(interval);
                        ObjectAnimator animY = ObjectAnimator.ofFloat(element, "y", cp1.getCpY() - copy.getHeight() / 2, cp2.getCpY() - copy.getHeight() / 2);
                        animY.setDuration(interval);
                        AnimatorSet anim = new AnimatorSet();
                        anim.play(animX).with(animY);

                        //show setting panel
                        settingPanel.setInterval(interval);
                        settingPanel.setAnimatorSet(anim);
                        settingPanel.setStart(viewSx, viewSy);
                        settingPanel.setElement(element);
                        settingPanel.setOriginal(selected);
                        app.getMainActivity().getFragmentManager()
                                .beginTransaction()
                                .add(R.id.main_container, settingPanel)
                                .commit();

                        break;
                }
                return true;
            }
        });
        paintCanvas.getMirror().addView(controlPoint);

    }

    private static class SettingPanel extends Fragment {
        private long interval;
        private AnimatorSet animatorSet;
        private float startX;
        private float startY;
        private CanvasElement element;
        private CanvasElement original;
        private EditText duration;

        public void setOriginal(CanvasElement original) {
            this.original = original;
        }

        public void setElement(CanvasElement element) {
            this.element = element;
        }

        public void setStart(float sx, float sy) {
            startX = sx;
            startY = sy;
        }

        public void setAnimatorSet(AnimatorSet animatorSet) {
            this.animatorSet = animatorSet;
        }

        public void setInterval(long interval) {
            this.interval = interval;
            if (duration != null) duration.setText(Long.toString(interval));
        }

        private void endGeneration() {
            app.getMainActivity().getFragmentManager().beginTransaction().remove(settingPanel).commit();
            app.getPaintCanvas().getMirror().removeAllViews();
            app.getPaintCanvas().getMirror().shadeOff();
            original.setVisibility(View.VISIBLE);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.translate_animation_setting, container, false);
            duration = (EditText) root.findViewById(R.id.translate_animation_duration);
            final Button play = (Button) root.findViewById(R.id.translate_animation_play);
            final Button restore = (Button) root.findViewById(R.id.translate_animation_restore);
            final Button ok = (Button) root.findViewById(R.id.translate_animation_ok);
            final Button cancel = (Button) root.findViewById(R.id.translate_animation_cancel);

            duration.setText(Long.toString(interval));

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long l = Long.parseLong(duration.getText().toString());
                    animatorSet.setDuration(l);
                    animatorSet.start();
                }
            });
            restore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animatorSet.end();
                    element.setX(startX);
                    element.setY(startY);
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Animator a : animatorSet.getChildAnimations()) {
                        a.setTarget(original);
                    }
                    AnimatorSet anim = app.getPaintCanvas().getAnimatorSet();
                    anim.play(anim.clone()).with(animatorSet);
                    app.getPaintCanvas().setAnimatorSet(anim);
                    //app.getPaintCanvas().getAnimatorSet().getChildAnimations().add(animatorSet);
                    endGeneration();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endGeneration();
                }
            });

            root.setOnTouchListener(new View.OnTouchListener() {
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
                        case MotionEvent.ACTION_UP:
                            break;
                    }
                    return true;
                }
            });
            return root;
        }
    }
}
