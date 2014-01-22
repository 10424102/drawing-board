package cn.edu.shu.android.drawingboard.core;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Iterator;

import cn.edu.shu.android.drawingboard.MyApplication;

/**
 * Created by yy on 1/22/14.
 */
public class Tool {
    private Element structure;
    private String iconPath;

    public void setCanvas(Canvas c){
        structure.setCanvas(c);
    }

    private void loadXML_icon(org.dom4j.Element root) throws PhraseXMLException {
        if (root.getName().toLowerCase().equals("icon")) {
            iconPath = root.attributeValue("url");
        }
        throw new PhraseXMLException("The root name is not icon");
    }

    public void loadXML(org.dom4j.Element root) throws PhraseXMLException {
        if (root.getName().toLowerCase().equals("drawingboard-tool")) {
            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                org.dom4j.Element e = (org.dom4j.Element) i.next();
                if (e.getName().toLowerCase().equals("ui")) {

                    loadXML_icon(e);

                } else if (e.getName().toLowerCase().equals("structure")) {

                    if (e.elements().size() == 1) {
                        org.dom4j.Element x = (org.dom4j.Element) e.elements().get(0);
                        if (x.getName().toLowerCase().equals("point")) {
                            structure = new Point();
                        }
                    } else if (e.elements().size() > 1) {
                        structure = new ElementGroup();
                        structure.loadXML(e);
                    } else {
                        new PhraseXMLException("Structure must contain one element.");
                    }

                } else if (e.getName().toLowerCase().equals("animation")) {

                } else {
                    throw new PhraseXMLException("Drawingboard-tool only support element: ui, structure and animation.");

                }
            }
        }
        throw new PhraseXMLException("The root name is not drawingboard-tool");
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
