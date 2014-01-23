package cn.edu.shu.android.drawingboard.core.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import cn.edu.shu.android.drawingboard.MyApplication;
import cn.edu.shu.android.drawingboard.core.elements.Element;
import cn.edu.shu.android.drawingboard.core.elements.ElementGroup;
import cn.edu.shu.android.drawingboard.core.elements.Point;
import cn.edu.shu.android.drawingboard.core.exception.ParserXMLException;
import cn.edu.shu.android.drawingboard.xml.Block;

/**
 * 画点，画直线，画圆，画矩形，背景填充，橡皮擦
 * @version 1.0
 * @author Yang Yang
 */
public class Tool {
    private Element structure;
    private String iconPath;

    public void setCanvas(Canvas c) {
        structure.setCanvas(c);
    }

    public void loadXML(Block root) throws ParserXMLException {
        if (root.getName().equalsIgnoreCase("drawingboard-tool")) {
            for (Iterator<Block> i = root.blockIterator(); i.hasNext(); ) {
                Block e = i.next();
                switch (e.getName().toLowerCase()) {

                    case "ui":

                        //TODO support different resolution, gif
                        List<Block> icon = e.getSubBlocksByName("icon");
                        int icon_num = icon.size();
                        if (icon_num == 0) {
                            //TODO use default icon
                        } else if (icon_num == 1) {
                            iconPath = icon.get(0).getAttrValue("url");
                        } else {
                            throw new ParserXMLException("More than one icon.");
                        }
                        break;

                    case "structure":

                        if (e.hasSubBlock()) {
                            if (e.subBlockCount() == 1) {
                                Block x = e.getFirstSubBlock();
                                if (x.getName().equalsIgnoreCase("point")) {
                                    structure = new Point();
                                } else if (x.getName().equalsIgnoreCase("line")) {
                                    //TODO single element line
                                }
                                structure.loadXML(x);
                            } else {
                                structure = new ElementGroup();
                                structure.loadXML(e);
                            }
                        } else {
                            new ParserXMLException("Structure must contain one element.");
                        }

                    case "animation":
                        break;

                    default:
                        throw new ParserXMLException("Drawingboard-tool only support elements: ui, structure and animation.");

                }
            }
        }
        throw new ParserXMLException("Try to use a non-drawingboard-tool xml to initialize the Tool object.");
    }

    public View getView(Context c) {
        Button btn = new Button(c);
        btn.setText("draw point");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Start drawing point, please touch.", Toast.LENGTH_LONG).show();
                MyApplication app = MyApplication.getInstance();
                structure.setCanvas(app.pc.getCanvas());
                structure.generate(v);
            }
        });
        return null;
    }
}
