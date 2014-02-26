package cn.edu.shu.android.group6.drawingboard.app.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.edu.shu.android.group6.drawingboard.app.R;


/**
 * Created by otakuplus on 14-2-22.
 */
public class ColorPickerFragment extends Fragment {
    private Button confirmButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.colorpick_fragment_layout, container, false);
        confirmButton = (Button) view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
