package cn.edu.shu.android.group6.drawingboard.app.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by yy on 2/5/14.
 */
public class PaintStylePickerDialog extends DialogFragment {

    int id;

    public interface OnStyleChangedListener {
        void styleChanged(Paint.Style style);
    }

    private OnStyleChangedListener listener;
    private Paint.Style initailStyle;

    public PaintStylePickerDialog(OnStyleChangedListener l, Paint.Style style) {
        listener = l;
        initailStyle = style;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (initailStyle == Paint.Style.FILL) id = 0;
        else if (initailStyle == Paint.Style.FILL_AND_STROKE) id = 1;
        else if (initailStyle == Paint.Style.STROKE) id = 2;

        builder.setTitle("Pick a style")
                .setSingleChoiceItems(new String[]{"FILL", "FILL AND STROKE", "STROKE"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        id = which;

                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (id) {
                            case 0:
                                Toast.makeText(getActivity(), "Fill", Toast.LENGTH_LONG).show();
                                listener.styleChanged(Paint.Style.FILL);
                                break;
                            case 1:
                                Toast.makeText(getActivity(), "Fill and Stroke", Toast.LENGTH_LONG).show();
                                listener.styleChanged(Paint.Style.FILL_AND_STROKE);
                                break;
                            case 2:
                                Toast.makeText(getActivity(), "Stroke", Toast.LENGTH_LONG).show();
                                listener.styleChanged(Paint.Style.STROKE);
                                break;
                            default:
                                Toast.makeText(getActivity(), "none", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
