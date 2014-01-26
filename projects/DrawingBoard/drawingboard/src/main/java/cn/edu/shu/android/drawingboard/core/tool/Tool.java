package cn.edu.shu.android.drawingboard.core.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.Button;

import java.util.Iterator;
import java.util.List;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.ElementGroup;
import cn.edu.shu.android.drawingboard.core.elements.Point;
import cn.edu.shu.android.drawingboard.core.exception.BuildToolException;
import cn.edu.shu.android.drawingboard.xml.Attr;
import cn.edu.shu.android.drawingboard.xml.Block;

/**
 * 画点，画直线，画圆，画矩形，背景填充，橡皮擦
 *
 * @author Yang Yang
 * @version 1.0
 */
public class Tool {
    private static int tool_count = 0;
    private Element structure;

    public String getIconPath() {
        return iconPath;
    }

    private String iconPath;
    private String name;
    private int id;

    public Tool() {
        setId(++tool_count);
    }

    public void setName(String name) {
        this.name = name;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public int getId() {
        return id;
    }

    public void setCanvas(Canvas c) {
        structure.setCanvas(c);
    }

    private void setUserInterface(Block b) throws BuildToolException {
        try {
            List<Block> icon = b.getSubBlocksByName("icon");
            int icon_num = icon.size();
            if (icon_num == 0) {
                //TODO use default icon
            } else if (icon_num == 1) {
                iconPath = icon.get(0).getAttrValue("url");
            } else {
                throw new BuildToolException("More than one icon.");
            }
        } catch (Exception e) {
            throw new BuildToolException(e);
        }
    }

    public void loadXML(Block root) throws BuildToolException {
        if (root.getName().equalsIgnoreCase("drawingboard-tool")) {

            for (Iterator<Attr> i = root.attrIterator(); i.hasNext(); ) {
                Attr a = i.next();
                switch (a.getName().toLowerCase()) {
                    case "name":
                        setName(a.getValue());
                        break;
                }
            }

            for (Iterator<Block> i = root.blockIterator(); i.hasNext(); ) {
                Block b = i.next();
                switch (b.getName().toLowerCase()) {

                    case "user-interface":
                        setUserInterface(b);
                        break;

                    case "structure":

                        try {
                            if (b.hasSubBlock()) {
                                if (b.subBlockCount() == 1) {
                                    Block x = b.getFirstSubBlock();
                                    switch (x.getName().toLowerCase()) {
                                        case "point":
                                            structure = new Point(x, 1);
                                            break;
                                        case "line":

                                            break;
                                    }
                                } else {
                                    structure = new ElementGroup(b);
                                }
                            } else {
                                new BuildToolException("Structure must contain one element.");
                            }
                        } catch (Exception e) {
                            throw new BuildToolException(e);
                        }

                    case "animation":
                        break;

                }
            }
        }
    }

    public View getView(Context c) {
        Button btn = new Button(c);
        btn.setText("draw point");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "Start drawing point, please touch.", Toast.LENGTH_LONG).show();

                structure.setCanvas(MyApplication.getInstance().getCanvas());
                structure.generate(MyApplication.getInstance().pc);
                //MyApplication.getInstance().update();
            }
        });
        return btn;
    }
    public void startUsing()
    {
        structure.generate(MyApplication.getInstance().pc);
    }
}
