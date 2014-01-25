package cn.edu.shu.android.drawingboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import cn.edu.shu.android.drawingboard.core.tool.Tool;
import cn.edu.shu.android.drawingboard.core.tool.ToolManager;

/**
 * Created by yy on 1/25/14.
 */
public class ToolboxFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ToolManager mToolManager = ToolManager.getInstance();
        List<Tool> list = mToolManager.getToolList();
        String[] s = new String[1];
        for(Tool t :list){
            s[0] = (t.getName());
        }




        builder.setTitle(getString(R.string.toolbox_title))
            .setItems(s
                ,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(),String.valueOf(which),Toast.LENGTH_LONG).show();
                }
            });
        return builder.create();
    }
}
