package cn.edu.shu.android.drawingboard;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yy on 2/21/14.
 */
public class Errorbox extends Fragment {
    private TextView content;
    private String msg;

    public void setContent(String msg) {
        this.msg = msg;
        if (content != null) content.setText(msg);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_errorbox, container, false);
        content = (TextView) root.findViewById(R.id.errorbox_content);
        content.setText(msg);
        return root;
    }
}
