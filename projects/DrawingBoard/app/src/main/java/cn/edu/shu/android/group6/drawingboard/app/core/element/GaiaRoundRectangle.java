package cn.edu.shu.android.group6.drawingboard.app.core.element;

import android.app.Fragment;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import cn.edu.shu.android.group6.drawingboard.app.R;
import cn.edu.shu.android.group6.drawingboard.app.core.view.CanvasElement;
import cn.edu.shu.android.group6.drawingboard.app.core.view.Draft;
import cn.edu.shu.android.group6.drawingboard.app.util.DimensionUtil;

/**
 * Created by yy on 3/1/14.
 */
public class GaiaRoundRectangle extends Gaia {
    private RectF rect;
    private static final SettingPanel settingPanel = new SettingPanel();
    float rx;
    float ry;

    public float getRx() {
        return rx;
    }

    public float getRy() {
        return ry;
    }

    public void setRy(float ry) {
        this.ry = ry;
    }

    public void setRx(float rx) {

        this.rx = rx;
    }

    private static final View.OnTouchListener gen = new View.OnTouchListener() {
        float sx;
        float sy;
        final Draft draft = app.getPaintCanvas().getDraft();

        private RectF f(float x1, float y1, float x2, float y2) {

            float cx = (x1 + x2) / 2;
            float cy = (y1 + y2) / 2;
            float w = Math.abs(x1 - x2) / 2;
            float h = Math.abs(y1 - y2) / 2;
            return new RectF(cx - w, cy - h, cx + w, cy + h);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    sx = event.getX();
                    sy = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    draft.clear();
                    draft.getCanvas().drawRect(f(sx, sy, event.getX(), event.getY()), app.getPaint());
                    draft.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    draft.clear();
                    draft.invalidate();
                    GaiaRoundRectangle gaia = new GaiaRoundRectangle();
                    gaia.set(app.getPaint(), f(sx, sy, event.getX(), event.getY()));
                    CanvasElement element = new CanvasElement(app.getContext(), gaia);
                    app.getPaintCanvas().getArtwork().addView(element);
                    settingPanel.setElement(element);
                    app.getMainActivity().getFragmentManager()
                            .beginTransaction()
                            .add(R.id.main_container, settingPanel)
                            .commit();
                    draft.setOnTouchListener(null);
                    break;
            }
            return true;
        }
    };

    public void set(Paint p, RectF r) {
        setPaint(p);
        setSize(r.width() + paint.getStrokeWidth() + 2, r.height() + paint.getStrokeWidth() + 2);
        setLeftTop(r.centerX() - width / 2, r.centerY() - height / 2);
        r.offset(-left, -top);
        rect = new RectF(r);
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.drawRoundRect(rect, rx, ry, paint);
    }

    @Override
    public void generate() {
        app.getPaintCanvas().getDraft().setOnTouchListener(gen);
    }

    private static class SettingPanel extends Fragment {
        private CanvasElement element;
        private GaiaRoundRectangle gaia;
        private float originalRx;
        private float originalRy;

        public void setElement(CanvasElement element) {
            this.element = element;
            gaia = (GaiaRoundRectangle) element.getGaia();
            originalRx = gaia.getRx();
            originalRy = gaia.getRy();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.round_rectangle_setting, container, false);
            final SeekBar rx = (SeekBar) root.findViewById(R.id.round_rectangle_rx_seek_bar);
            final SeekBar ry = (SeekBar) root.findViewById(R.id.round_rectangle_ry_seek_bar);
            rx.setMax((int) (gaia.getWidth() / 2));
            ry.setMax((int) (gaia.getHeight() / 2));
            rx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        gaia.setRx(progress);
                        element.invalidate();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            ry.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        gaia.setRy(progress);
                        element.invalidate();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            Button ok = (Button) root.findViewById(R.id.round_rectangle_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app.getMainActivity().getFragmentManager().beginTransaction().remove(settingPanel).commit();
                    rx.setProgress(0);
                    ry.setProgress(0);
                    app.getPaintCanvas().getDraft().setOnTouchListener(gen);
                }
            });
            Button cancel = (Button) root.findViewById(R.id.round_rectangle_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gaia.setRx(originalRx);
                    gaia.setRy(originalRy);
                    element.invalidate();
                    app.getMainActivity().getFragmentManager().beginTransaction().remove(settingPanel).commit();
                    rx.setProgress(0);
                    ry.setProgress(0);
                    app.getPaintCanvas().getDraft().setOnTouchListener(gen);
                }
            });
            if (DimensionUtil.dpToPx(250) < container.getWidth() - element.getX() - element.getWidth()) {
                root.setX(element.getX() + gaia.getWidth() + 30);
                root.setY(element.getY());
            } else if (DimensionUtil.dpToPx(250) < element.getX()) {
                root.setX(element.getX() - DimensionUtil.dpToPx(250) - 30);
                root.setY(element.getY());
            } else {
                root.setX(0);
                root.setY(0);
            }
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
