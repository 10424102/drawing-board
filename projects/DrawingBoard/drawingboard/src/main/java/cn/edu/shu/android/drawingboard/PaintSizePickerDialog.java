package cn.edu.shu.android.drawingboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by yy on 2/5/14.
 */
public class PaintSizePickerDialog extends DialogFragment {

    public PaintSizePickerDialog(OnSizeChangedListener listener,
                                 int initialSize) {
        mListener = listener;
        mInitialSize = initialSize;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.size_picker, null);
        final SeekBar seekBar = (SeekBar) v.findViewById(R.id.size_picker_seekbar);
        seekBar.setProgress(mInitialSize);
        final TextView textView = (TextView) v.findViewById(R.id.size_picker_text);
        textView.setText("" + mInitialSize);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    textView.setText("" + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        builder.setView(v)
                .setTitle("Pick a size")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.sizeChanged(seekBar.getProgress());
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    public interface OnSizeChangedListener {
        void sizeChanged(int size);
    }

    private OnSizeChangedListener mListener;
    private int mInitialSize;

}
