package cn.edu.shu.android.drawingboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;

/**
 * Created by yy on 2/5/14.
 */
public class PaintStylePickerDialog extends DialogFragment {
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
        int id = -1;
        if (initailStyle == Paint.Style.FILL) id = 0;
        else if (initailStyle == Paint.Style.FILL_AND_STROKE) id = 1;
        else if (initailStyle == Paint.Style.STROKE) id = 2;

        builder
                .setTitle("Pick a style")
                .setSingleChoiceItems(new String[]{"FILL", "FILL AND STROKE", "STROKE"}, id, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                listener.styleChanged(Paint.Style.FILL);
                                break;
                            case 1:
                                listener.styleChanged(Paint.Style.FILL_AND_STROKE);
                                break;
                            case 2:
                                listener.styleChanged(Paint.Style.STROKE);
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
