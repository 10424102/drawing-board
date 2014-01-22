package cn.edu.shu.android.drawingboard.core;

import android.graphics.Canvas;

/**
 * Created by yy on 1/22/14.
 */
public abstract class Element  implements Generable{
    public static int id_count = 0;
    protected int id;
    protected int zindex;
    protected Position center;
    protected Canvas canvas;

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Element() {
        id_count = id_count + 1;
        id = id_count;
    }

    public int getZindex() {

        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }

    public Position getCenter() {

        return center;
    }

    public void setCenter(Position center) {
        this.center = center;
    }

    public int getId() {
        return id;
    }

    public abstract void draw();

    public abstract void transform(Transform t);

    public abstract void loadXML(org.dom4j.Element root) throws PhraseXMLException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;

        Element element = (Element) o;

        if (id != element.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                '}';
    }

}
