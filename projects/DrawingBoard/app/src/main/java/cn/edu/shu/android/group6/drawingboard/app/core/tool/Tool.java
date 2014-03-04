package cn.edu.shu.android.group6.drawingboard.app.core.tool;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.shu.android.group6.drawingboard.app.App;
import cn.edu.shu.android.group6.drawingboard.app.R;
import cn.edu.shu.android.group6.drawingboard.app.core.Generable;
import cn.edu.shu.android.group6.drawingboard.app.util.BitmapUtil;

/**
 * Created by yy on 2/27/14.
 */
public class Tool {
    private static int idCount = 0;
    protected static final App app = App.getInstance();
    private Generable generator;
    private String name = "No name";
    private View view;
    private boolean oneoff = false;
    private String dirPath;
    private int id;

    public int getId() {
        return id;
    }

    public Tool() {
        id = idCount++;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getDirPath() {
        return dirPath;
    }

    public Generable getGenerator() {
        return generator;
    }

    public boolean isOneoff() {
        return oneoff;
    }

    public void setOneoff(boolean oneoff) {
        this.oneoff = oneoff;
    }

    public View getView() {
        if (view == null) {
            view = app.getMainActivity().getLayoutInflater().inflate(R.layout.default_tool_item, null);
            final ImageView icon = (ImageView) view.findViewById(R.id.tool_icon);
            final TextView text = (TextView) view.findViewById(R.id.tool_name);
            text.setText(name);
            icon.setImageBitmap(BitmapUtil.getBitmapFromAssets("tools/CocaCola/Coca-Cola-icon.png"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startUsing();
                    if (!oneoff) {
                        text.setTextColor(Color.RED);
                    }
                }
            });
        }
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setGenerator(Generable generator) {
        this.generator = generator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void startUsing() {
        if (generator != null) generator.generate();
        if (!oneoff) {
            if (app.getCurrentTool() != null) {
                ((Button) app.getCurrentTool().getView()).setTextColor(Color.BLACK);
            }
            app.setCurrentTool(this);
        }
    }
}
