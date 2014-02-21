package cn.edu.shu.android.drawingboard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.shu.android.drawingboard.core.tool.Tool;
import cn.edu.shu.android.drawingboard.core.tool.ToolManager;
import cn.edu.shu.android.drawingboard.util.BitmapUtil;

/**
 * Created by yy on 1/25/14.
 */
public class ToolboxFragment extends DialogFragment {

    private List<ToolManager.ToolDisplayModel> list;

    private MyAdapter adapter;

    private MyApplication app = MyApplication.getInstance();

    public ToolboxFragment() {

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        list = ToolManager.getInstance().getToolDisplayModelList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        GridView grid = new GridView(getActivity());
        grid.setNumColumns(4);
        adapter = new MyAdapter(getActivity());
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tool t = ToolManager.getInstance().getTool(list.get(position).getId());
                t.startUsing();
                dismiss();
            }
        });
        builder.setView(grid).setTitle(getResources().getString(R.string.toolbox_title));
        return builder.create();
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return list.get(position).getId();
        }

        private class Holder {
            RelativeLayout wrapper;
            ImageView icon;
            TextView text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.toolbox_item, parent, false);
                holder = new Holder();
                holder.wrapper = (RelativeLayout) convertView.findViewById(R.id.toolbox_item_wrapper);
                holder.text = (TextView) convertView.findViewById(R.id.toolbox_item_text);
                holder.icon = (ImageView) convertView.findViewById(R.id.toolbox_item_icon);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            ToolManager.ToolDisplayModel t = (ToolManager.ToolDisplayModel) getItem(position);
            holder.text.setText(t.getName());

            //handle icon
            BitmapFactory.Options decodeOption = new BitmapFactory.Options();
            decodeOption.outWidth = (int) (parent.getWidth() * 0.6);
            decodeOption.outHeight = decodeOption.outWidth;
            Bitmap bmp = BitmapFactory.decodeFile(t.getIconPath(), decodeOption);
            if (bmp == null) {
                bmp = BitmapUtil.getBitmapResource(R.drawable.default_tool_icon, parent.getWidth() * 0.6, parent.getWidth() * 0.6);
            }

            holder.icon.setImageBitmap(bmp);

            return convertView;
        }
    }

}
