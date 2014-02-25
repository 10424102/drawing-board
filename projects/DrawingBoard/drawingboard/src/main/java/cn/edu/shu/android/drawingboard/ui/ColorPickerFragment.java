package cn.edu.shu.android.drawingboard.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.edu.shu.android.drawingboard.R;

/**
 * Created by otakuplus on 14-2-22.
 */
public class ColorPickerFragment extends Fragment {
    private Button confirmButton;
    private TransparentRegulator transparentRegulator;
    private ColorCircleView colorCircleView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.colorpick_fragment_layout, container, false);

        confirmButton = (Button) view.findViewById(R.id.confirm_button);
        transparentRegulator = (TransparentRegulator) view.findViewById(R.id.alphabar);
        colorCircleView = (ColorCircleView) view.findViewById(R.id.colorcircle);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.hide(ColorPickerFragment.this);
                fragmentTransaction.commit();
            }
        });

        colorCircleView.setOnColorChangeListener(new ColorCircleView.OnColorChangeListener() {
            @Override
            public void OnColorChange(int color) {
                transparentRegulator.setColor(color);
            }
        });

        return view;
    }
}
